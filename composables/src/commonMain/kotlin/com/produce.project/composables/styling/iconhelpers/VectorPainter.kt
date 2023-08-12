package com.produce.project.composables.styling.iconhelpers

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

const val RootGroupName = "VectorRootGroup"

@Composable
fun rememberVectorPainter(
    defaultWidth: Dp,
    defaultHeight: Dp,
    viewportWidth: Float = Float.NaN,
    viewportHeight: Float = Float.NaN,
    name: String = RootGroupName,
    tintColor: Color = Color.Unspecified,
    tintBlendMode: BlendMode = BlendMode.SrcIn,
    content: @Composable (viewportWidth: Float, viewportHeight: Float) -> Unit
): VectorPainter {
    val density = LocalDensity.current
    val widthPx = with(density) { defaultWidth.toPx() }
    val heightPx = with(density) { defaultHeight.toPx() }

    val vpWidth = if (viewportWidth.isNaN()) widthPx else viewportWidth
    val vpHeight = if (viewportHeight.isNaN()) heightPx else viewportHeight

    val painter = remember { VectorPainter() }.apply {
        // This assignment is thread safe as the internal Size parameter is
        // backed by a mutableState object
        size = Size(widthPx, heightPx)
        RenderVector(name, vpWidth, vpHeight, content)
    }
    SideEffect {
        // Initialize the intrinsic color filter if a tint color is provided on the
        // vector itself. Note this tint can be overridden by an explicit ColorFilter
        // provided on the Modifier.paint call
        painter.intrinsicColorFilter = if (tintColor != Color.Unspecified) {
            ColorFilter.tint(tintColor, tintBlendMode)
        } else {
            null
        }
    }
    return painter
}

@Composable
fun rememberVectorPainter(image: ImageVector) =
    rememberVectorPainter(
        defaultWidth = image.defaultWidth,
        defaultHeight = image.defaultHeight,
        viewportWidth = image.viewportWidth,
        viewportHeight = image.viewportHeight,
        name = image.name,
        tintColor = image.tintColor,
        tintBlendMode = image.tintBlendMode,
        content = { _, _ -> RenderVectorGroup(group = image.root) }
    )

class VectorPainter internal constructor() : Painter() {

    internal var size by mutableStateOf(Size.Zero)

    internal var intrinsicColorFilter: ColorFilter?
        get() = vector.intrinsicColorFilter
        set(value) {
            vector.intrinsicColorFilter = value
        }

    private val vector = VectorComponent().apply {
        invalidateCallback = {
            isDirty = true
        }
    }

    private var composition: Composition? = null

    private fun composeVector(
        parent: CompositionContext,
        composable: @Composable (viewportWidth: Float, viewportHeight: Float) -> Unit
    ): Composition {
        val existing = composition
        val next = if (existing == null || existing.isDisposed) {
            Composition(
                VectorApplier(vector.root),
                parent
            )
        } else {
            existing
        }
        composition = next
        next.setContent {
            composable(vector.viewportWidth, vector.viewportHeight)
        }
        return next
    }

    private var isDirty by mutableStateOf(true)

    @Composable
    internal fun RenderVector(
        name: String,
        viewportWidth: Float,
        viewportHeight: Float,
        content: @Composable (viewportWidth: Float, viewportHeight: Float) -> Unit
    ) {
        vector.apply {
            this.name = name
            this.viewportWidth = viewportWidth
            this.viewportHeight = viewportHeight
        }
        val composition = composeVector(
            rememberCompositionContext(),
            content
        )

        DisposableEffect(composition) {
            onDispose {
                composition.dispose()
            }
        }
    }

    private var currentAlpha: Float = 1.0f
    private var currentColorFilter: ColorFilter? = null

    override val intrinsicSize: Size
        get() = size

    override fun DrawScope.onDraw() {
        with(vector) {
            draw(currentAlpha, currentColorFilter ?: intrinsicColorFilter)
        }
        // This conditional is necessary to obtain invalidation callbacks as the state is
        // being read here which adds this callback to the snapshot observation
        if (isDirty) {
            isDirty = false
        }
    }

    override fun applyAlpha(alpha: Float): Boolean {
        currentAlpha = alpha
        return true
    }

    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        currentColorFilter = colorFilter
        return true
    }
}

sealed class VectorProperty<T> {
    object Rotation : VectorProperty<Float>()
    object PivotX : VectorProperty<Float>()
    object PivotY : VectorProperty<Float>()
    object ScaleX : VectorProperty<Float>()
    object ScaleY : VectorProperty<Float>()
    object TranslateX : VectorProperty<Float>()
    object TranslateY : VectorProperty<Float>()
    object PathData : VectorProperty<List<PathNode>>()
    object Fill : VectorProperty<Brush?>()
    object FillAlpha : VectorProperty<Float>()
    object Stroke : VectorProperty<Brush?>()
    object StrokeLineWidth : VectorProperty<Float>()
    object StrokeAlpha : VectorProperty<Float>()
    object TrimPathStart : VectorProperty<Float>()
    object TrimPathEnd : VectorProperty<Float>()
    object TrimPathOffset : VectorProperty<Float>()
}

interface VectorConfig {
    fun <T> getOrDefault(property: VectorProperty<T>, defaultValue: T): T {
        return defaultValue
    }
}

@Composable
fun RenderVectorGroup(
    group: VectorGroup,
    configs: Map<String, VectorConfig> = emptyMap()
) {
    for (vectorNode in group) {
        if (vectorNode is VectorPath) {
            val config = configs[vectorNode.name] ?: object : VectorConfig {}
            Path(
                pathData = config.getOrDefault(
                    VectorProperty.PathData,
                    vectorNode.pathData
                ),
                pathFillType = vectorNode.pathFillType,
                name = vectorNode.name,
                fill = config.getOrDefault(
                    VectorProperty.Fill,
                    vectorNode.fill
                ),
                fillAlpha = config.getOrDefault(
                    VectorProperty.FillAlpha,
                    vectorNode.fillAlpha
                ),
                stroke = config.getOrDefault(
                    VectorProperty.Stroke,
                    vectorNode.stroke
                ),
                strokeAlpha = config.getOrDefault(
                    VectorProperty.StrokeAlpha,
                    vectorNode.strokeAlpha
                ),
                strokeLineWidth = config.getOrDefault(
                    VectorProperty.StrokeLineWidth,
                    vectorNode.strokeLineWidth
                ),
                strokeLineCap = vectorNode.strokeLineCap,
                strokeLineJoin = vectorNode.strokeLineJoin,
                strokeLineMiter = vectorNode.strokeLineMiter,
                trimPathStart = config.getOrDefault(
                    VectorProperty.TrimPathStart,
                    vectorNode.trimPathStart
                ),
                trimPathEnd = config.getOrDefault(
                    VectorProperty.TrimPathEnd,
                    vectorNode.trimPathEnd
                ),
                trimPathOffset = config.getOrDefault(
                    VectorProperty.TrimPathOffset,
                    vectorNode.trimPathOffset
                )
            )
        } else if (vectorNode is VectorGroup) {
            val config = configs[vectorNode.name] ?: object : VectorConfig {}
            Group(
                name = vectorNode.name,
                rotation = config.getOrDefault(
                    VectorProperty.Rotation,
                    vectorNode.rotation
                ),
                scaleX = config.getOrDefault(
                    VectorProperty.ScaleX,
                    vectorNode.scaleX
                ),
                scaleY = config.getOrDefault(
                    VectorProperty.ScaleY,
                    vectorNode.scaleY
                ),
                translationX = config.getOrDefault(
                    VectorProperty.TranslateX,
                    vectorNode.translationX
                ),
                translationY = config.getOrDefault(
                    VectorProperty.TranslateY,
                    vectorNode.translationY
                ),
                pivotX = config.getOrDefault(
                    VectorProperty.PivotX,
                    vectorNode.pivotX
                ),
                pivotY = config.getOrDefault(
                    VectorProperty.PivotY,
                    vectorNode.pivotY
                ),
                clipPathData = config.getOrDefault(
                    VectorProperty.PathData,
                    vectorNode.clipPathData
                )
            ) {
                RenderVectorGroup(group = vectorNode, configs = configs)
            }
        }
    }
}
