fun main() {
    val stopwatch = System.currentTimeMillis()

    val width = 80
    val height = 60
    val maxIterations = 1000

    val xmin = -2.0
    val xmax = 1.0
    val ymin = -1.5
    val ymax = 1.5

    val dx = (xmax - xmin) / (width - 1)
    val dy = (ymax - ymin) / (height - 1)

    for (y in 0 until height) {
        for (x in 0 until width) {
            var zx = xmin + x * dx
            var zy = ymin + y * dy
            val cx = zx
            val cy = zy

            var iteration = 0
            while (iteration < maxIterations && (zx * zx + zy * zy) < 4.0) {
                val newZx = zx * zx - zy * zy + cx
                val newZy = 2.0 * zx * zy + cy
                zx = newZx
                zy = newZy
                iteration++
            }

            // Print the iteration count or some result to the console
            println("($x, $y): $iteration")
        }
    }

    val elapsedTime = System.currentTimeMillis() - stopwatch
    println("Execution time: $elapsedTime ms")
}
