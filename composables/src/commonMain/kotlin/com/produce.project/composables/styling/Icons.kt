package com.produce.project.composables.styling

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.produce.project.composables.styling.iconhelpers.path
import com.produce.project.composables.styling.iconhelpers.ImageVector
import com.produce.project.composables.styling.iconhelpers.ImageVector.Builder
import com.produce.project.composables.styling.iconhelpers.group
import kotlin.math.roundToInt

val IcLanguage: ImageVector
    get() {
        if (icLanguage != null) return icLanguage!!
        icLanguage = Builder(name = "IcLanguage", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
            viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 0.5f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.8184f, 18.7218f)
                lineTo(9.9975f, 18.9112f)
                lineTo(10.1792f, 18.7243f)
                curveTo(10.7809f, 18.1054f, 11.283f, 17.397f, 11.6856f, 16.6003f)
                curveTo(12.0882f, 15.8037f, 12.415f, 14.8632f, 12.6684f, 13.782f)
                lineTo(12.7404f, 13.475f)
                horizontalLineTo(12.425f)
                horizontalLineTo(7.6f)
                horizontalLineTo(7.2849f)
                lineTo(7.3565f, 13.7818f)
                curveTo(7.5935f, 14.7974f, 7.9119f, 15.7157f, 8.313f, 16.5349f)
                curveTo(8.7152f, 17.3565f, 9.2169f, 18.0859f, 9.8184f, 18.7218f)
                close()
                moveTo(7.7943f, 18.4866f)
                lineTo(8.4849f, 18.7222f)
                lineTo(8.0838f, 18.1126f)
                curveTo(7.6764f, 17.4933f, 7.3256f, 16.8244f, 7.0314f, 16.1053f)
                curveTo(6.7378f, 15.3876f, 6.4917f, 14.5767f, 6.2943f, 13.6717f)
                lineTo(6.2513f, 13.475f)
                horizontalLineTo(6.05f)
                horizontalLineTo(2.3f)
                horizontalLineTo(1.8826f)
                lineTo(2.0796f, 13.843f)
                curveTo(2.7248f, 15.0485f, 3.4778f, 16.0062f, 4.3426f, 16.7068f)
                curveTo(5.2059f, 17.406f, 6.36f, 17.9973f, 7.7943f, 18.4866f)
                close()
                moveTo(11.9411f, 18.0876f)
                lineTo(11.5525f, 18.6783f)
                lineTo(12.2261f, 18.4631f)
                curveTo(13.4544f, 18.0708f, 14.5609f, 17.4815f, 15.5437f, 16.6952f)
                curveTo(16.5283f, 15.9075f, 17.3209f, 14.9564f, 17.9201f, 13.8435f)
                lineTo(18.1186f, 13.475f)
                horizontalLineTo(17.7f)
                horizontalLineTo(13.975f)
                horizontalLineTo(13.778f)
                lineTo(13.7319f, 13.6665f)
                curveTo(13.5176f, 14.5569f, 13.2668f, 15.362f, 12.9802f, 16.0826f)
                curveTo(12.6945f, 16.8008f, 12.3481f, 17.469f, 11.9411f, 18.0876f)
                close()
                moveTo(1.56f, 12.295f)
                lineTo(1.6125f, 12.475f)
                horizontalLineTo(1.8f)
                horizontalLineTo(5.775f)
                horizontalLineTo(6.0543f)
                lineTo(6.0235f, 12.1974f)
                curveTo(5.9741f, 11.7528f, 5.9456f, 11.3561f, 5.9374f, 11.0067f)
                curveTo(5.9291f, 10.6502f, 5.925f, 10.298f, 5.925f, 9.95f)
                curveTo(5.925f, 9.5365f, 5.9333f, 9.1701f, 5.9497f, 8.8503f)
                curveTo(5.966f, 8.5326f, 5.9987f, 8.1763f, 6.0481f, 7.781f)
                lineTo(6.0832f, 7.5f)
                horizontalLineTo(5.8f)
                horizontalLineTo(1.8f)
                horizontalLineTo(1.6125f)
                lineTo(1.56f, 7.68f)
                curveTo(1.4408f, 8.0888f, 1.3584f, 8.4599f, 1.3146f, 8.7924f)
                curveTo(1.2711f, 9.1229f, 1.25f, 9.5093f, 1.25f, 9.95f)
                curveTo(1.25f, 10.391f, 1.2712f, 10.7889f, 1.3143f, 11.1428f)
                curveTo(1.3579f, 11.5003f, 1.4403f, 11.8846f, 1.56f, 12.295f)
                close()
                moveTo(7.0771f, 12.257f)
                lineTo(7.1052f, 12.475f)
                horizontalLineTo(7.325f)
                horizontalLineTo(12.7f)
                horizontalLineTo(12.9198f)
                lineTo(12.9479f, 12.257f)
                curveTo(13.015f, 11.7372f, 13.0575f, 11.3093f, 13.0747f, 10.9753f)
                curveTo(13.0916f, 10.6457f, 13.1f, 10.304f, 13.1f, 9.95f)
                curveTo(13.1f, 9.6125f, 13.0916f, 9.2872f, 13.0746f, 8.974f)
                curveTo(13.0575f, 8.6567f, 13.0149f, 8.2373f, 12.9479f, 7.718f)
                lineTo(12.9198f, 7.5f)
                horizontalLineTo(12.7f)
                horizontalLineTo(7.325f)
                horizontalLineTo(7.1052f)
                lineTo(7.0771f, 7.718f)
                curveTo(7.01f, 8.2373f, 6.9675f, 8.6567f, 6.9504f, 8.974f)
                curveTo(6.9334f, 9.2872f, 6.925f, 9.6125f, 6.925f, 9.95f)
                curveTo(6.925f, 10.304f, 6.9334f, 10.6457f, 6.9503f, 10.9753f)
                curveTo(6.9675f, 11.3093f, 7.01f, 11.7372f, 7.0771f, 12.257f)
                close()
                moveTo(13.9512f, 12.2001f)
                lineTo(13.9238f, 12.475f)
                horizontalLineTo(14.2f)
                horizontalLineTo(18.2f)
                horizontalLineTo(18.3875f)
                lineTo(18.44f, 12.295f)
                curveTo(18.5597f, 11.8846f, 18.6421f, 11.5003f, 18.6857f, 11.1428f)
                curveTo(18.7288f, 10.7889f, 18.75f, 10.391f, 18.75f, 9.95f)
                curveTo(18.75f, 9.5093f, 18.7289f, 9.1229f, 18.6854f, 8.7924f)
                curveTo(18.6416f, 8.4599f, 18.5592f, 8.0888f, 18.44f, 7.68f)
                lineTo(18.3875f, 7.5f)
                horizontalLineTo(18.2f)
                horizontalLineTo(14.225f)
                horizontalLineTo(13.9527f)
                lineTo(13.9759f, 7.7713f)
                curveTo(14.0258f, 8.3539f, 14.0589f, 8.7966f, 14.0754f, 9.101f)
                curveTo(14.0918f, 9.4055f, 14.1f, 9.6885f, 14.1f, 9.95f)
                curveTo(14.1f, 10.311f, 14.0877f, 10.6504f, 14.0632f, 10.9683f)
                curveTo(14.0384f, 11.2907f, 14.0011f, 11.7011f, 13.9512f, 12.2001f)
                close()
                moveTo(13.7063f, 6.3056f)
                lineTo(13.7506f, 6.5f)
                horizontalLineTo(13.95f)
                horizontalLineTo(17.7f)
                horizontalLineTo(18.0967f)
                lineTo(17.9255f, 6.1421f)
                curveTo(17.3599f, 4.9595f, 16.5826f, 3.9709f, 15.5937f, 3.1798f)
                curveTo(14.6061f, 2.3897f, 13.4692f, 1.8326f, 12.1864f, 1.5077f)
                lineTo(11.5507f, 1.3466f)
                lineTo(11.9179f, 1.89f)
                curveTo(12.3244f, 2.4917f, 12.6705f, 3.143f, 12.9559f, 3.8442f)
                curveTo(13.2414f, 4.5457f, 13.4919f, 5.3656f, 13.7063f, 6.3056f)
                close()
                moveTo(7.3584f, 6.1856f)
                lineTo(7.2746f, 6.5f)
                horizontalLineTo(7.6f)
                horizontalLineTo(12.45f)
                horizontalLineTo(12.7572f)
                lineTo(12.6948f, 6.1992f)
                curveTo(12.5066f, 5.2923f, 12.1901f, 4.4162f, 11.7463f, 3.5712f)
                curveTo(11.3037f, 2.7286f, 10.7831f, 1.9812f, 10.1839f, 1.3306f)
                lineTo(10.0217f, 1.1546f)
                lineTo(9.8388f, 1.3089f)
                curveTo(9.2753f, 1.7843f, 8.8056f, 2.4047f, 8.4264f, 3.1632f)
                curveTo(8.0491f, 3.9177f, 7.694f, 4.9274f, 7.3584f, 6.1856f)
                close()
                moveTo(2.0764f, 6.1382f)
                lineTo(1.8955f, 6.5f)
                horizontalLineTo(2.3f)
                horizontalLineTo(6.075f)
                horizontalLineTo(6.2792f)
                lineTo(6.32f, 6.2999f)
                curveTo(6.5009f, 5.4117f, 6.7303f, 4.6223f, 7.0071f, 3.9304f)
                curveTo(7.2841f, 3.2378f, 7.6348f, 2.5648f, 8.0596f, 1.9112f)
                lineTo(8.4076f, 1.3758f)
                lineTo(7.7886f, 1.5327f)
                curveTo(6.5065f, 1.8574f, 5.3818f, 2.406f, 4.4184f, 3.1801f)
                curveTo(3.4546f, 3.9546f, 2.6744f, 4.9422f, 2.0764f, 6.1382f)
                close()
                moveTo(10.0f, 19.75f)
                curveTo(8.6321f, 19.75f, 7.3577f, 19.4937f, 6.174f, 18.983f)
                curveTo(4.9852f, 18.47f, 3.9535f, 17.775f, 3.0768f, 16.8982f)
                curveTo(2.2006f, 16.0221f, 1.51f, 14.9869f, 1.0053f, 13.7903f)
                curveTo(0.5023f, 12.5977f, 0.25f, 11.3184f, 0.25f, 9.95f)
                curveTo(0.25f, 8.5815f, 0.5023f, 7.3111f, 1.0049f, 6.1358f)
                curveTo(1.5094f, 4.9558f, 2.2001f, 3.9285f, 3.0768f, 3.0518f)
                curveTo(3.9529f, 2.1756f, 4.9837f, 1.4894f, 6.1714f, 0.9932f)
                curveTo(7.3557f, 0.4984f, 8.631f, 0.25f, 10.0f, 0.25f)
                curveTo(11.369f, 0.25f, 12.6443f, 0.4984f, 13.8286f, 0.9932f)
                curveTo(15.0163f, 1.4894f, 16.0471f, 2.1756f, 16.9232f, 3.0518f)
                curveTo(17.7999f, 3.9285f, 18.4906f, 4.9558f, 18.9951f, 6.1358f)
                curveTo(19.4977f, 7.3111f, 19.75f, 8.5815f, 19.75f, 9.95f)
                curveTo(19.75f, 11.3184f, 19.4977f, 12.5977f, 18.9946f, 13.7903f)
                curveTo(18.49f, 14.9869f, 17.7994f, 16.0221f, 16.9232f, 16.8982f)
                curveTo(16.0465f, 17.775f, 15.0148f, 18.47f, 13.826f, 18.983f)
                curveTo(12.6423f, 19.4937f, 11.3679f, 19.75f, 10.0f, 19.75f)
                close()
            }
        }.build()
        return icLanguage!!
    }

private var icLanguage: ImageVector? = null

val IcTick: ImageVector
    get() {
        if (icTick != null) return icTick!!
        icTick = Builder(name = "Tick", defaultWidth = 30.0.dp, defaultHeight = 30.0.dp,
            viewportWidth = 30.0f, viewportHeight = 30.0f).apply {
            path(fill = SolidColor(Color(0xFF272B35)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(15.0f, 0.0f)
                curveTo(6.7293f, 0.0f, 0.0f, 6.7293f, 0.0f, 15.0f)
                curveTo(0.0f, 23.2707f, 6.7293f, 30.0f, 15.0f, 30.0f)
                curveTo(23.2707f, 30.0f, 30.0f, 23.2707f, 30.0f, 15.0f)
                curveTo(30.0f, 6.7293f, 23.2707f, 0.0f, 15.0f, 0.0f)
                close()
                moveTo(23.3835f, 11.0526f)
                lineTo(13.797f, 20.5639f)
                curveTo(13.2331f, 21.1278f, 12.3308f, 21.1654f, 11.7293f, 20.6015f)
                lineTo(6.6541f, 15.9774f)
                curveTo(6.0526f, 15.4135f, 6.015f, 14.4737f, 6.5413f, 13.8722f)
                curveTo(7.1053f, 13.2707f, 8.0451f, 13.2331f, 8.6466f, 13.797f)
                lineTo(12.6692f, 17.4812f)
                lineTo(21.2406f, 8.9098f)
                curveTo(21.8421f, 8.3083f, 22.782f, 8.3083f, 23.3835f, 8.9098f)
                curveTo(23.985f, 9.5113f, 23.985f, 10.4511f, 23.3835f, 11.0526f)
                close()
            }
        }.build()
        return icTick!!
    }

private var icTick: ImageVector? = null

val IcRejected: ImageVector
    get() {
        if (icRejected != null) return icRejected!!
        icRejected = Builder(name = "Rejected", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
            viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(16.5f, 15.5f)
                moveToRelative(-11.5f, 0.0f)
                arcToRelative(11.5f, 11.5f, 0.0f, true, true, 23.0f, 0.0f)
                arcToRelative(11.5f, 11.5f, 0.0f, true, true, -23.0f, 0.0f)
            }
            group {
                path(fill = SolidColor(Color(0xFFBB0A21)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                    moveTo(27.313f, 4.681f)
                    curveTo(25.075f, 2.445f, 22.224f, 0.922f, 19.121f, 0.306f)
                    curveTo(16.018f, -0.31f, 12.802f, 0.008f, 9.88f, 1.219f)
                    curveTo(6.957f, 2.431f, 4.459f, 4.482f, 2.702f, 7.113f)
                    curveTo(0.945f, 9.743f, 0.007f, 12.836f, 0.007f, 16.0f)
                    curveTo(0.007f, 19.164f, 0.945f, 22.257f, 2.702f, 24.887f)
                    curveTo(4.459f, 27.518f, 6.957f, 29.569f, 9.88f, 30.781f)
                    curveTo(12.802f, 31.992f, 16.018f, 32.31f, 19.121f, 31.694f)
                    curveTo(22.224f, 31.078f, 25.075f, 29.556f, 27.313f, 27.319f)
                    curveTo(30.311f, 24.315f, 31.995f, 20.244f, 31.995f, 16.0f)
                    curveTo(31.995f, 11.756f, 30.311f, 7.685f, 27.313f, 4.681f)
                    close()
                    moveTo(22.596f, 20.721f)
                    curveTo(22.727f, 20.843f, 22.832f, 20.99f, 22.904f, 21.154f)
                    curveTo(22.977f, 21.317f, 23.016f, 21.493f, 23.019f, 21.672f)
                    curveTo(23.022f, 21.851f, 22.989f, 22.029f, 22.922f, 22.195f)
                    curveTo(22.855f, 22.361f, 22.756f, 22.511f, 22.629f, 22.638f)
                    curveTo(22.503f, 22.765f, 22.352f, 22.865f, 22.186f, 22.932f)
                    curveTo(22.021f, 22.999f, 21.843f, 23.032f, 21.664f, 23.03f)
                    curveTo(21.485f, 23.027f, 21.309f, 22.988f, 21.145f, 22.916f)
                    curveTo(20.981f, 22.844f, 20.834f, 22.739f, 20.712f, 22.608f)
                    lineTo(15.996f, 17.891f)
                    lineTo(11.282f, 22.608f)
                    curveTo(11.031f, 22.859f, 10.691f, 22.999f, 10.337f, 22.998f)
                    curveTo(9.982f, 22.998f, 9.643f, 22.856f, 9.393f, 22.605f)
                    curveTo(9.143f, 22.354f, 9.002f, 22.014f, 9.003f, 21.66f)
                    curveTo(9.004f, 21.306f, 9.145f, 20.966f, 9.396f, 20.716f)
                    lineTo(14.11f, 16.0f)
                    lineTo(9.394f, 11.284f)
                    curveTo(9.157f, 11.031f, 9.028f, 10.696f, 9.034f, 10.35f)
                    curveTo(9.04f, 10.004f, 9.18f, 9.673f, 9.425f, 9.428f)
                    curveTo(9.669f, 9.183f, 10.0f, 9.043f, 10.346f, 9.037f)
                    curveTo(10.693f, 9.032f, 11.028f, 9.161f, 11.281f, 9.398f)
                    lineTo(15.996f, 14.107f)
                    lineTo(20.712f, 9.391f)
                    curveTo(20.963f, 9.141f, 21.303f, 9.001f, 21.657f, 9.002f)
                    curveTo(22.011f, 9.002f, 22.351f, 9.144f, 22.601f, 9.395f)
                    curveTo(22.851f, 9.646f, 22.991f, 9.986f, 22.991f, 10.34f)
                    curveTo(22.99f, 10.694f, 22.849f, 11.034f, 22.598f, 11.284f)
                    lineTo(17.882f, 16.0f)
                    lineTo(22.596f, 20.721f)
                    close()
                }
            }
        }
            .build()
        return icRejected!!
    }

private var icRejected: ImageVector? = null