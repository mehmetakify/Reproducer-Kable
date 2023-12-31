package com.produce.project.composables.styling.iconhelpers

import androidx.compose.ui.graphics.Path
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.math.*

class PathParser {

    private data class PathPoint(var x: Float = 0.0f, var y: Float = 0.0f) {
        fun reset() {
            x = 0.0f
            y = 0.0f
        }
    }


    @OptIn(ExperimentalContracts::class)
    fun <T> List<T>.fastForEach(action: (T) -> Unit) {
        contract { callsInPlace(action) }
        for (index in indices) {
            val item = get(index)
            action(item)
        }
    }

    private val nodes = mutableListOf<PathNode>()

    fun clear() {
        nodes.clear()
    }

    private val currentPoint = PathPoint()
    private val ctrlPoint = PathPoint()
    private val segmentPoint = PathPoint()
    private val reflectiveCtrlPoint = PathPoint()

    /**
     * Parses the path string to create a collection of PathNode instances with their corresponding
     * arguments
     * throws an IllegalArgumentException or NumberFormatException if the parameters are invalid
     */
    fun parsePathString(pathData: String): PathParser {
        nodes.clear()

        var start = 0
        var end = 1
        while (end < pathData.length) {
            end = nextStart(pathData, end)
            val s = pathData.substring(start, end).trim { it <= ' ' }
            if (s.isNotEmpty()) {
                val args = getFloats(s)
                addNode(s[0], args)
            }

            start = end
            end++
        }
        if (end - start == 1 && start < pathData.length) {
            addNode(pathData[start], FloatArray(0))
        }

        return this
    }

    fun addPathNodes(nodes: List<PathNode>): PathParser {
        this.nodes.addAll(nodes)
        return this
    }

    fun toNodes(): List<PathNode> = nodes

    fun toPath(target: Path = Path()): Path {
        target.reset()
        currentPoint.reset()
        ctrlPoint.reset()
        segmentPoint.reset()
        reflectiveCtrlPoint.reset()

        var previousNode: PathNode? = null
        nodes.fastForEach { node ->
            if (previousNode == null) previousNode = node
            when (node) {
                is PathNode.Close -> close(target)
                is PathNode.RelativeMoveTo -> node.relativeMoveTo(target)
                is PathNode.MoveTo -> node.moveTo(target)
                is PathNode.RelativeLineTo -> node.relativeLineTo(target)
                is PathNode.LineTo -> node.lineTo(target)
                is PathNode.RelativeHorizontalTo -> node.relativeHorizontalTo(target)
                is PathNode.HorizontalTo -> node.horizontalTo(target)
                is PathNode.RelativeVerticalTo -> node.relativeVerticalTo(target)
                is PathNode.VerticalTo -> node.verticalTo(target)
                is PathNode.RelativeCurveTo -> node.relativeCurveTo(target)
                is PathNode.CurveTo -> node.curveTo(target)
                is PathNode.RelativeReflectiveCurveTo ->
                    node.relativeReflectiveCurveTo(previousNode!!.isCurve, target)
                is PathNode.ReflectiveCurveTo -> node.reflectiveCurveTo(previousNode!!.isCurve, target)
                is PathNode.RelativeQuadTo -> node.relativeQuadTo(target)
                is PathNode.QuadTo -> node.quadTo(target)
                is PathNode.RelativeReflectiveQuadTo ->
                    node.relativeReflectiveQuadTo(previousNode!!.isQuad, target)
                is PathNode.ReflectiveQuadTo -> node.reflectiveQuadTo(previousNode!!.isQuad, target)
                is PathNode.RelativeArcTo -> node.relativeArcTo(target)
                is PathNode.ArcTo -> node.arcTo(target)
            }
            previousNode = node
        }
        return target
    }

    private fun close(target: Path) {
        currentPoint.x = segmentPoint.x
        currentPoint.y = segmentPoint.y
        ctrlPoint.x = segmentPoint.x
        ctrlPoint.y = segmentPoint.y

        target.close()
        target.moveTo(currentPoint.x, currentPoint.y)
    }

    private fun PathNode.RelativeMoveTo.relativeMoveTo(target: Path) {
        currentPoint.x += dx
        currentPoint.y += dy
        target.relativeMoveTo(dx, dy)
        segmentPoint.x = currentPoint.x
        segmentPoint.y = currentPoint.y
    }

    private fun PathNode.MoveTo.moveTo(target: Path) {
        currentPoint.x = x
        currentPoint.y = y
        target.moveTo(x, y)
        segmentPoint.x = currentPoint.x
        segmentPoint.y = currentPoint.y
    }

    private fun PathNode.RelativeLineTo.relativeLineTo(target: Path) {
        target.relativeLineTo(dx, dy)
        currentPoint.x += dx
        currentPoint.y += dy
    }

    private fun PathNode.LineTo.lineTo(target: Path) {
        target.lineTo(x, y)
        currentPoint.x = x
        currentPoint.y = y
    }

    private fun PathNode.RelativeHorizontalTo.relativeHorizontalTo(target: Path) {
        target.relativeLineTo(dx, 0.0f)
        currentPoint.x += dx
    }

    private fun PathNode.HorizontalTo.horizontalTo(target: Path) {
        target.lineTo(x, currentPoint.y)
        currentPoint.x = x
    }

    private fun PathNode.RelativeVerticalTo.relativeVerticalTo(target: Path) {
        target.relativeLineTo(0.0f, dy)
        currentPoint.y += dy
    }

    private fun PathNode.VerticalTo.verticalTo(target: Path) {
        target.lineTo(currentPoint.x, y)
        currentPoint.y = y
    }

    private fun PathNode.RelativeCurveTo.relativeCurveTo(target: Path) {
        target.relativeCubicTo(
            dx1, dy1,
            dx2, dy2,
            dx3, dy3
        )
        ctrlPoint.x = currentPoint.x + dx2
        ctrlPoint.y = currentPoint.y + dy2
        currentPoint.x += dx3
        currentPoint.y += dy3
    }

    private fun PathNode.CurveTo.curveTo(target: Path) {
        target.cubicTo(
            x1, y1,
            x2, y2,
            x3, y3
        )
        ctrlPoint.x = x2
        ctrlPoint.y = y2
        currentPoint.x = x3
        currentPoint.y = y3
    }

    private fun PathNode.RelativeReflectiveCurveTo.relativeReflectiveCurveTo(
        prevIsCurve: Boolean,
        target: Path
    ) {
        if (prevIsCurve) {
            reflectiveCtrlPoint.x = currentPoint.x - ctrlPoint.x
            reflectiveCtrlPoint.y = currentPoint.y - ctrlPoint.y
        } else {
            reflectiveCtrlPoint.reset()
        }

        target.relativeCubicTo(
            reflectiveCtrlPoint.x, reflectiveCtrlPoint.y,
            dx1, dy1,
            dx2, dy2
        )
        ctrlPoint.x = currentPoint.x + dx1
        ctrlPoint.y = currentPoint.y + dy1
        currentPoint.x += dx2
        currentPoint.y += dy2
    }

    private fun PathNode.ReflectiveCurveTo.reflectiveCurveTo(prevIsCurve: Boolean, target: Path) {
        if (prevIsCurve) {
            reflectiveCtrlPoint.x = 2 * currentPoint.x - ctrlPoint.x
            reflectiveCtrlPoint.y = 2 * currentPoint.y - ctrlPoint.y
        } else {
            reflectiveCtrlPoint.x = currentPoint.x
            reflectiveCtrlPoint.y = currentPoint.y
        }

        target.cubicTo(
            reflectiveCtrlPoint.x, reflectiveCtrlPoint.y,
            x1, y1, x2, y2
        )
        ctrlPoint.x = x1
        ctrlPoint.y = y1
        currentPoint.x = x2
        currentPoint.y = y2
    }

    private fun PathNode.RelativeQuadTo.relativeQuadTo(target: Path) {
        target.relativeQuadraticBezierTo(dx1, dy1, dx2, dy2)
        ctrlPoint.x = currentPoint.x + dx1
        ctrlPoint.y = currentPoint.y + dy1
        currentPoint.x += dx2
        currentPoint.y += dy2
    }

    private fun PathNode.QuadTo.quadTo(target: Path) {
        target.quadraticBezierTo(x1, y1, x2, y2)
        ctrlPoint.x = x1
        ctrlPoint.y = y1
        currentPoint.x = x2
        currentPoint.y = y2
    }

    private fun PathNode.RelativeReflectiveQuadTo.relativeReflectiveQuadTo(
        prevIsQuad: Boolean,
        target: Path
    ) {
        if (prevIsQuad) {
            reflectiveCtrlPoint.x = currentPoint.x - ctrlPoint.x
            reflectiveCtrlPoint.y = currentPoint.y - ctrlPoint.y
        } else {
            reflectiveCtrlPoint.reset()
        }

        target.relativeQuadraticBezierTo(
            reflectiveCtrlPoint.x,
            reflectiveCtrlPoint.y, dx, dy
        )
        ctrlPoint.x = currentPoint.x + reflectiveCtrlPoint.x
        ctrlPoint.y = currentPoint.y + reflectiveCtrlPoint.y
        currentPoint.x += dx
        currentPoint.y += dy
    }

    private fun PathNode.ReflectiveQuadTo.reflectiveQuadTo(prevIsQuad: Boolean, target: Path) {
        if (prevIsQuad) {
            reflectiveCtrlPoint.x = 2 * currentPoint.x - ctrlPoint.x
            reflectiveCtrlPoint.y = 2 * currentPoint.y - ctrlPoint.y
        } else {
            reflectiveCtrlPoint.x = currentPoint.x
            reflectiveCtrlPoint.y = currentPoint.y
        }
        target.quadraticBezierTo(
            reflectiveCtrlPoint.x,
            reflectiveCtrlPoint.y, x, y
        )
        ctrlPoint.x = reflectiveCtrlPoint.x
        ctrlPoint.y = reflectiveCtrlPoint.y
        currentPoint.x = x
        currentPoint.y = y
    }

    private fun PathNode.RelativeArcTo.relativeArcTo(target: Path) {
        val arcStartX = arcStartDx + currentPoint.x
        val arcStartY = arcStartDy + currentPoint.y

        drawArc(
            target,
            currentPoint.x.toDouble(),
            currentPoint.y.toDouble(),
            arcStartX.toDouble(),
            arcStartY.toDouble(),
            horizontalEllipseRadius.toDouble(),
            verticalEllipseRadius.toDouble(),
            theta.toDouble(),
            isMoreThanHalf,
            isPositiveArc
        )
        currentPoint.x = arcStartX
        currentPoint.y = arcStartY

        ctrlPoint.x = currentPoint.x
        ctrlPoint.y = currentPoint.y
    }

    private fun PathNode.ArcTo.arcTo(target: Path) {
        drawArc(
            target,
            currentPoint.x.toDouble(),
            currentPoint.y.toDouble(),
            arcStartX.toDouble(),
            arcStartY.toDouble(),
            horizontalEllipseRadius.toDouble(),
            verticalEllipseRadius.toDouble(),
            theta.toDouble(),
            isMoreThanHalf,
            isPositiveArc
        )

        currentPoint.x = arcStartX
        currentPoint.y = arcStartY

        ctrlPoint.x = currentPoint.x
        ctrlPoint.y = currentPoint.y
    }

    private fun drawArc(
        p: Path,
        x0: Double,
        y0: Double,
        x1: Double,
        y1: Double,
        a: Double,
        b: Double,
        theta: Double,
        isMoreThanHalf: Boolean,
        isPositiveArc: Boolean
    ) {

        /* Convert rotation angle from degrees to radians */
        val thetaD = theta.toRadians()
        /* Pre-compute rotation matrix entries */
        val cosTheta = cos(thetaD)
        val sinTheta = sin(thetaD)
        /* Transform (x0, y0) and (x1, y1) into unit space */
        /* using (inverse) rotation, followed by (inverse) scale */
        val x0p = (x0 * cosTheta + y0 * sinTheta) / a
        val y0p = (-x0 * sinTheta + y0 * cosTheta) / b
        val x1p = (x1 * cosTheta + y1 * sinTheta) / a
        val y1p = (-x1 * sinTheta + y1 * cosTheta) / b

        /* Compute differences and averages */
        val dx = x0p - x1p
        val dy = y0p - y1p
        val xm = (x0p + x1p) / 2
        val ym = (y0p + y1p) / 2
        /* Solve for intersecting unit circles */
        val dsq = dx * dx + dy * dy
        if (dsq == 0.0) {
            return /* Points are coincident */
        }
        val disc = 1.0 / dsq - 1.0 / 4.0
        if (disc < 0.0) {
            val adjust = (sqrt(dsq) / 1.99999).toFloat()
            drawArc(
                p, x0, y0, x1, y1, a * adjust,
                b * adjust, theta, isMoreThanHalf, isPositiveArc
            )
            return /* Points are too far apart */
        }
        val s = sqrt(disc)
        val sdx = s * dx
        val sdy = s * dy
        var cx: Double
        var cy: Double
        if (isMoreThanHalf == isPositiveArc) {
            cx = xm - sdy
            cy = ym + sdx
        } else {
            cx = xm + sdy
            cy = ym - sdx
        }

        val eta0 = atan2(y0p - cy, x0p - cx)

        val eta1 = atan2(y1p - cy, x1p - cx)

        var sweep = eta1 - eta0
        if (isPositiveArc != (sweep >= 0)) {
            if (sweep > 0) {
                sweep -= 2 * PI
            } else {
                sweep += 2 * PI
            }
        }

        cx *= a
        cy *= b
        val tcx = cx
        cx = cx * cosTheta - cy * sinTheta
        cy = tcx * sinTheta + cy * cosTheta

        arcToBezier(
            p, cx, cy, a, b, x0, y0, thetaD,
            eta0, sweep
        )
    }

    private fun arcToBezier(
        p: Path,
        cx: Double,
        cy: Double,
        a: Double,
        b: Double,
        e1x: Double,
        e1y: Double,
        theta: Double,
        start: Double,
        sweep: Double
    ) {
        var eta1x = e1x
        var eta1y = e1y
        // Taken from equations at: http://spaceroots.org/documents/ellipse/node8.html
        // and http://www.spaceroots.org/documents/ellipse/node22.html

        // Maximum of 45 degrees per cubic Bezier segment
        val numSegments = ceil(abs(sweep * 4 / PI)).toInt()

        var eta1 = start
        val cosTheta = cos(theta)
        val sinTheta = sin(theta)
        val cosEta1 = cos(eta1)
        val sinEta1 = sin(eta1)
        var ep1x = (-a * cosTheta * sinEta1) - (b * sinTheta * cosEta1)
        var ep1y = (-a * sinTheta * sinEta1) + (b * cosTheta * cosEta1)

        val anglePerSegment = sweep / numSegments
        for (i in 0 until numSegments) {
            val eta2 = eta1 + anglePerSegment
            val sinEta2 = sin(eta2)
            val cosEta2 = cos(eta2)
            val e2x = cx + (a * cosTheta * cosEta2) - (b * sinTheta * sinEta2)
            val e2y = cy + (a * sinTheta * cosEta2) + (b * cosTheta * sinEta2)
            val ep2x = (-a * cosTheta * sinEta2) - (b * sinTheta * cosEta2)
            val ep2y = (-a * sinTheta * sinEta2) + (b * cosTheta * cosEta2)
            val tanDiff2 = tan((eta2 - eta1) / 2)
            val alpha = sin(eta2 - eta1) * (sqrt(4 + 3.0 * tanDiff2 * tanDiff2) - 1) / 3
            val q1x = eta1x + alpha * ep1x
            val q1y = eta1y + alpha * ep1y
            val q2x = e2x - alpha * ep2x
            val q2y = e2y - alpha * ep2y

            // Adding this no-op call to workaround a proguard related issue.
            // p.relativeLineTo(0.0, 0.0)

            p.cubicTo(
                q1x.toFloat(),
                q1y.toFloat(),
                q2x.toFloat(),
                q2y.toFloat(),
                e2x.toFloat(),
                e2y.toFloat()
            )
            eta1 = eta2
            eta1x = e2x
            eta1y = e2y
            ep1x = ep2x
            ep1y = ep2y
        }
    }

    private fun addNode(cmd: Char, args: FloatArray) {
        nodes.addAll(cmd.toPathNodes(args))
    }

    private fun nextStart(s: String, end: Int): Int {
        var index = end
        var c: Char

        while (index < s.length) {
            c = s[index]
            if (((c - 'A') * (c - 'Z') <= 0 || (c - 'a') * (c - 'z') <= 0) &&
                c != 'e' && c != 'E'
            ) {
                return index
            }
            index++
        }
        return index
    }

    private fun getFloats(s: String): FloatArray {
        if (s[0] == 'z' || s[0] == 'Z') {
            return FloatArray(0)
        }
        val results = FloatArray(s.length)
        var count = 0
        var startPosition = 1
        var endPosition: Int

        val result = ExtractFloatResult()
        val totalLength = s.length

        // The startPosition should always be the first character of the
        // current number, and endPosition is the character after the current
        // number.
        while (startPosition < totalLength) {
            extract(s, startPosition, result)
            endPosition = result.endPosition

            if (startPosition < endPosition) {
                results[count++] =
                    s.substring(startPosition, endPosition).toFloat()
            }

            if (result.endWithNegativeOrDot) {
                // Keep the '-' or '.' sign with next number.
                startPosition = endPosition
            } else {
                startPosition = endPosition + 1
            }
        }
        return copyOfRange(results, 0, count)
    }

    private fun copyOfRange(original: FloatArray, start: Int, end: Int): FloatArray {
        if (start > end) {
            throw IllegalArgumentException()
        }
        val originalLength = original.size
        if (start < 0 || start > originalLength) {
            throw IndexOutOfBoundsException()
        }
        val resultLength = end - start
        val copyLength = minOf(resultLength, originalLength - start)
        val result = FloatArray(resultLength)
        original.copyInto(result, 0, start, start + copyLength)
        return result
    }

    private fun extract(s: String, start: Int, result: ExtractFloatResult) {
        // Now looking for ' ', ',', '.' or '-' from the start.
        var currentIndex = start
        var foundSeparator = false
        result.endWithNegativeOrDot = false
        var secondDot = false
        var isExponential = false
        while (currentIndex < s.length) {
            val isPrevExponential = isExponential
            isExponential = false
            val currentChar = s[currentIndex]
            when (currentChar) {
                ' ', ',' -> foundSeparator = true
                '-' ->
                    // The negative sign following a 'e' or 'E' is not a separator.
                    if (currentIndex != start && !isPrevExponential) {
                        foundSeparator = true
                        result.endWithNegativeOrDot = true
                    }
                '.' ->
                    if (!secondDot) {
                        secondDot = true
                    } else {
                        // This is the second dot, and it is considered as a separator.
                        foundSeparator = true
                        result.endWithNegativeOrDot = true
                    }
                'e', 'E' -> isExponential = true
            }
            if (foundSeparator) {
                break
            }
            currentIndex++
        }
        // When there is nothing found, then we put the end position to the end
        // of the string.
        result.endPosition = currentIndex
    }

    private data class ExtractFloatResult(
        // We need to return the position of the next separator and whether the
        // next float starts with a '-' or a '.'.
        var endPosition: Int = 0,
        var endWithNegativeOrDot: Boolean = false
    )

    private fun Float.toRadians(): Float = this / 180f * PI.toFloat()
    private fun Double.toRadians(): Double = this / 180 * PI
}