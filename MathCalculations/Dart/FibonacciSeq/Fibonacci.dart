int fibonacciRecursive(int n) {
  return (n <= 1) ? n : fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
}

void main() {
  final index = 35; 
  final stopwatch = Stopwatch()..start();
  final fibonacciResult = fibonacciRecursive(index);
  stopwatch.stop();
  double elapsedTime = stopwatch.elapsedMilliseconds.toDouble();
  String formattedTime = elapsedTime.toStringAsFixed(2);
  print(formattedTime);

  //print('Fibonacci sequence at index $index: $fibonacciResult');
}
