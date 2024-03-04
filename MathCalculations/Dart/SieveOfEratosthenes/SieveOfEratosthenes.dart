List<int> sieveOfEratosthenes(int limit) {
  List<bool> isPrime = List<bool>.filled(limit + 1, true);
  List<int> primes = [];

  for (int num = 2; num <= limit; num++) {
    if (isPrime[num]) {
      primes.add(num);

      int multiple = 2 * num;
      while (multiple <= limit) {
        isPrime[multiple] = false;
        multiple += num;
      }
    }
  }

  return primes;
}

void main() {
  int limit = 1000000; 
  final stopwatch = Stopwatch()..start();
  List<int> primeNumbers = sieveOfEratosthenes(limit);
  stopwatch.stop();
  double elapsedTime = stopwatch.elapsedMilliseconds.toDouble();
  String formattedTime = elapsedTime.toStringAsFixed(2);
  print(formattedTime);
  //print("Prime numbers up to $limit: $primeNumbers");
}
