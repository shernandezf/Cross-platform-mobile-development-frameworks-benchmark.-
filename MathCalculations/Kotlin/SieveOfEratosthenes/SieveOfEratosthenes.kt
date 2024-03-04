fun sieveOfEratosthenes(limit: Int): List<Int> {
    
    val isPrime = BooleanArray(limit + 1) { true }
    val primes = mutableListOf<Int>()

    for (num in 2..limit) {
        if (isPrime[num]) {
            primes.add(num)

            var multiple = 2 * num
            while (multiple <= limit) {
                isPrime[multiple] = false
                multiple += num
            }
        }
    }

    return primes
}

fun main() {
    val limit = 1000000 
    val stopwatch = System.currentTimeMillis()
    sieveOfEratosthenes(limit)
    var elapsedTime = System.currentTimeMillis() - stopwatch
    var formattedTime = String.format("%.2f", elapsedTime.toDouble())
    formattedTime = formattedTime.replace(',', '.')
    println("$formattedTime")
    //println("Prime numbers up to $limit: $primeNumbers")
}
