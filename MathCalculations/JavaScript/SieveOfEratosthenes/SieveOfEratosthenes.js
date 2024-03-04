function sieveOfEratosthenes(limit) {
    const isPrime = new Array(limit + 1).fill(true);
    const primes = [];

    for (let num = 2; num <= limit; num++) {
        if (isPrime[num]) {
            primes.push(num);

            let multiple = 2 * num;
            while (multiple <= limit) {
                isPrime[multiple] = false;
                multiple += num;
            }
        }
    }

    return primes;
}

const limit = 1000000;
const startTime = performance.now();
const primeNumbers = sieveOfEratosthenes(limit);
const endTime = performance.now();
const elapsedTime = endTime - startTime;
const formattedTime = elapsedTime.toFixed(2);
console.log(`${formattedTime}`);

//console.log(`Prime numbers up to ${limit}: ${primeNumbers}`);
