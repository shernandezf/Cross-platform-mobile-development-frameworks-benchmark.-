fun fibonacciRecursive(n: Int): Long {
    return if (n <= 1) {
        n.toLong()
    } else {
        fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2)
    }
}

fun main() {
    val stopwatch = System.currentTimeMillis()
    val n = 35
    val fibonacciResult = fibonacciRecursive(n)

    var elapsedTime = System.currentTimeMillis() - stopwatch
    var formattedTime = String.format("%.2f", elapsedTime.toDouble())
    formattedTime = formattedTime.replace(',', '.')
    println("$formattedTime")
    //println("Fibonacci sequence at index $n: $fibonacciResult")
}
