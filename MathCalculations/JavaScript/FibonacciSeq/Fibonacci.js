function fibonacciRecursive(n) {
    return n <= 1 ? n : fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
  }
  
const index = 35;
const startTime = performance.now(); // Set your desired Fibonacci sequence index here
const fibonacciResult = fibonacciRecursive(index);
const endTime = performance.now();
const elapsedTime = endTime - startTime;
const formattedTime = elapsedTime.toFixed(2);
console.log(`${formattedTime}`);
//console.log(`Fibonacci sequence at index ${index}: ${fibonacciResult}`);
  