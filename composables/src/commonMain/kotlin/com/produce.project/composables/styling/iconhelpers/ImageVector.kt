package com.produce.project.composables.styling.iconhelpers

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp

@Immutable
class ImageVector internal constructor(
    val name: String,
    val defaultWidth: Dp,
    val defaultHeight: Dp,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val root: VectorGroup,
    val tintColor: Color,
    val tintBlendMode: BlendMode
) {
    @Suppress("MissingGetterMatchingBuilder")
    class Builder(
        private val name: String = DefaultGroupName,
        private val defaultWidth: Dp,
        private val defaultHeight: Dp,
        private val viewportWidth: Float,
        private val viewportHeight: Float,
        private val tintColor: Color = Color.Unspecified,
        private val tintBlendMode: BlendMode = BlendMode.SrcIn
    ) {
        private val nodes = Stack<GroupParams>()
        private var root = GroupParams()
        private var isConsumed = false
        private val currentGroup: GroupParams
            get() = nodes.peek()

        init {
            nodes.push(root)
        }

        @Suppress("MissingGetterMatchingBuilder")
        fun addGroup(
            name: String = DefaultGroupName,
            rotate: Float = DefaultRotation,
            pivotX: Float = DefaultPivotX,
            pivotY: Float = DefaultPivotY,
            scaleX: Float = DefaultScaleX,
            scaleY: Float = DefaultScaleY,
            translationX: Float = DefaultTranslationX,
            translationY: Float = DefaultTranslationY,
            clipPathData: List<PathNode> = EmptyPath
        ): Builder {
            ensureNotConsumed()
            val group = GroupParams(
                name,
                rotate,
                pivotX,
                pivotY,
                scaleX,
                scaleY,
                translationX,
                translationY,
                clipPathData
            )
            nodes.push(group)
            return this
        }

        fun clearGroup(): Builder {
            ensureNotConsumed()
            val popped = nodes.pop()
            currentGroup.children.add(popped.asVectorGroup())
            return this
        }

        @Suppress("MissingGetterMatchingBuilder")
        fun addPath(
            pathData: List<PathNode>,
            pathFillType: PathFillType = DefaultFillType,
            name: String = DefaultPathName,
            fill: Brush? = null,
            fillAlpha: Float = 1.0f,
            stroke: Brush? = null,
            strokeAlpha: Float = 1.0f,
            strokeLineWidth: Float = DefaultStrokeLineWidth,
            strokeLineCap: StrokeCap = DefaultStrokeLineCap,
            strokeLineJoin: StrokeJoin = DefaultStrokeLineJoin,
            strokeLineMiter: Float = DefaultStrokeLineMiter,
            trimPathStart: Float = DefaultTrimPathStart,
            trimPathEnd: Float = DefaultTrimPathEnd,
            trimPathOffset: Float = DefaultTrimPathOffset
        ): Builder {
            ensureNotConsumed()
            currentGroup.children.add(
                VectorPath(
                    name,
                    pathData,
                    pathFillType,
                    fill,
                    fillAlpha,
                    stroke,
                    strokeAlpha,
                    strokeLineWidth,
                    strokeLineCap,
                    strokeLineJoin,
                    strokeLineMiter,
                    trimPathStart,
                    trimPathEnd,
                    trimPathOffset
                )
            )
            return this
        }

        fun build(): ImageVector {
            ensureNotConsumed()
            // pop all groups except for the root
            while (nodes.size > 1) {
                clearGroup()
            }

            val vectorImage = ImageVector(
                name,
                defaultWidth,
                defaultHeight,
                viewportWidth,
                viewportHeight,
                root.asVectorGroup(),
                tintColor,
                tintBlendMode
            )

            isConsumed = true

            return vectorImage
        }

        private fun ensureNotConsumed() {
            check(!isConsumed) {
                "ImageVector.Builder is single use, create a new instance " +
                        "to create a new ImageVector"
            }
        }

        private fun GroupParams.asVectorGroup(): VectorGroup =
            VectorGroup(
                name,
                rotate,
                pivotX,
                pivotY,
                scaleX,
                scaleY,
                translationX,
                translationY,
                clipPathData,
                children
            )

        private class GroupParams(
            var name: String = DefaultGroupName,
            var rotate: Float = DefaultRotation,
            var pivotX: Float = DefaultPivotX,
            var pivotY: Float = DefaultPivotY,
            var scaleX: Float = DefaultScaleX,
            var scaleY: Float = DefaultScaleY,
            var translationX: Float = DefaultTranslationX,
            var translationY: Float = DefaultTranslationY,
            var clipPathData: List<PathNode> = EmptyPath,
            var children: MutableList<VectorNode> = mutableListOf()
        )
    }

    companion object {} // ktlint-disable no-empty-class-body

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImageVector) return false

        if (name != other.name) return false
        if (defaultWidth != other.defaultWidth) return false
        if (defaultHeight != other.defaultHeight) return false
        if (viewportWidth != other.viewportWidth) return false
        if (viewportHeight != other.viewportHeight) return false
        if (root != other.root) return false
        if (tintColor != other.tintColor) return false
        if (tintBlendMode != other.tintBlendMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + defaultWidth.hashCode()
        result = 31 * result + defaultHeight.hashCode()
        result = 31 * result + viewportWidth.hashCode()
        result = 31 * result + viewportHeight.hashCode()
        result = 31 * result + root.hashCode()
        result = 31 * result + tintColor.hashCode()
        result = 31 * result + tintBlendMode.hashCode()
        return result
    }
}

sealed class VectorNode

@Immutable
class VectorGroup internal constructor(
    val name: String = DefaultGroupName,
    val rotation: Float = DefaultRotation,
    val pivotX: Float = DefaultPivotX,
    val pivotY: Float = DefaultPivotY,
    val scaleX: Float = DefaultScaleX,
    val scaleY: Float = DefaultScaleY,
    val translationX: Float = DefaultTranslationX,
    val translationY: Float = DefaultTranslationY,
    val clipPathData: List<PathNode> = EmptyPath,
    private val children: List<VectorNode> = emptyList()
) : VectorNode(), Iterable<VectorNode> {

    val size: Int
        get() = children.size

    operator fun get(index: Int): VectorNode {
        return children[index]
    }

    override fun iterator(): Iterator<VectorNode> {
        return object : Iterator<VectorNode> {

            val it = children.iterator()

            override fun hasNext(): Boolean = it.hasNext()

            override fun next(): VectorNode = it.next()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is VectorGroup) return false

        if (name != other.name) return false
        if (rotation != other.rotation) return false
        if (pivotX != other.pivotX) return false
        if (pivotY != other.pivotY) return false
        if (scaleX != other.scaleX) return false
        if (scaleY != other.scaleY) return false
        if (translationX != other.translationX) return false
        if (translationY != other.translationY) return false
        if (clipPathData != other.clipPathData) return false
        if (children != other.children) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + rotation.hashCode()
        result = 31 * result + pivotX.hashCode()
        result = 31 * result + pivotY.hashCode()
        result = 31 * result + scaleX.hashCode()
        result = 31 * result + scaleY.hashCode()
        result = 31 * result + translationX.hashCode()
        result = 31 * result + translationY.hashCode()
        result = 31 * result + clipPathData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }
}

@Immutable
class VectorPath internal constructor(

    val name: String = DefaultPathName,
    val pathData: List<PathNode>,
    val pathFillType: PathFillType,
    val fill: Brush? = null,
    val fillAlpha: Float = 1.0f,
    val stroke: Brush? = null,
    val strokeAlpha: Float = 1.0f,
    val strokeLineWidth: Float = DefaultStrokeLineWidth,
    val strokeLineCap: StrokeCap = DefaultStrokeLineCap,
    val strokeLineJoin: StrokeJoin = DefaultStrokeLineJoin,
    val strokeLineMiter: Float = DefaultStrokeLineMiter,
    val trimPathStart: Float = DefaultTrimPathStart,
    val trimPathEnd: Float = DefaultTrimPathEnd,
    val trimPathOffset: Float = DefaultTrimPathOffset
) : VectorNode() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as VectorPath

        return name == other.name &&
                fill == other.fill &&
                fillAlpha == other.fillAlpha &&
                stroke == other.stroke &&
                strokeAlpha == other.strokeAlpha &&
                strokeLineWidth == other.strokeLineWidth &&
                strokeLineCap == other.strokeLineCap &&
                strokeLineJoin == other.strokeLineJoin &&
                strokeLineMiter == other.strokeLineMiter &&
                trimPathStart == other.trimPathStart &&
                trimPathEnd == other.trimPathEnd &&
                trimPathOffset == other.trimPathOffset &&
                pathFillType == other.pathFillType &&
                pathData == other.pathData
    }

    override fun hashCode(): Int {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 *
                name.hashCode() + (stroke?.hashCode() ?: 0))
                + pathData.hashCode()) + (fill?.hashCode() ?: 0))
                + fillAlpha.hashCode()) + strokeAlpha.hashCode()) +
                strokeLineWidth.hashCode()) + strokeLineCap.hashCode()) + strokeLineJoin.hashCode()) +
                strokeLineMiter.hashCode()) + trimPathStart.hashCode()) + trimPathEnd.hashCode()) +
                trimPathOffset.hashCode()) + pathFillType.hashCode()
    }
}

inline fun ImageVector.Builder.path(
    name: String = DefaultPathName,
    fill: Brush? = null,
    fillAlpha: Float = 1.0f,
    stroke: Brush? = null,
    strokeAlpha: Float = 1.0f,
    strokeLineWidth: Float = DefaultStrokeLineWidth,
    strokeLineCap: StrokeCap = DefaultStrokeLineCap,
    strokeLineJoin: StrokeJoin = DefaultStrokeLineJoin,
    strokeLineMiter: Float = DefaultStrokeLineMiter,
    pathFillType: PathFillType = DefaultFillType,
    pathBuilder: PathBuilder.() -> Unit
) = addPath(
    PathData(pathBuilder),
    pathFillType,
    name,
    fill,
    fillAlpha,
    stroke,
    strokeAlpha,
    strokeLineWidth,
    strokeLineCap,
    strokeLineJoin,
    strokeLineMiter
)

inline fun ImageVector.Builder.group(
    name: String = DefaultGroupName,
    rotate: Float = DefaultRotation,
    pivotX: Float = DefaultPivotX,
    pivotY: Float = DefaultPivotY,
    scaleX: Float = DefaultScaleX,
    scaleY: Float = DefaultScaleY,
    translationX: Float = DefaultTranslationX,
    translationY: Float = DefaultTranslationY,
    clipPathData: List<PathNode> = EmptyPath,
    block: ImageVector.Builder.() -> Unit
) = apply {
    addGroup(
        name,
        rotate,
        pivotX,
        pivotY,
        scaleX,
        scaleY,
        translationX,
        translationY,
        clipPathData
    )
    block()
    clearGroup()
}

@Suppress("INLINE_CLASS_DEPRECATED", "EXPERIMENTAL_FEATURE_WARNING")
private inline class Stack<T>(private val backing: ArrayList<T> = ArrayList<T>()) {
    val size: Int get() = backing.size

    fun push(value: T) = backing.add(value)
    fun pop(): T = backing.removeAt(size - 1)
    fun peek(): T = backing[size - 1]
    fun isEmpty() = backing.isEmpty()
    fun isNotEmpty() = !isEmpty()
    fun clear() = backing.clear()
}
