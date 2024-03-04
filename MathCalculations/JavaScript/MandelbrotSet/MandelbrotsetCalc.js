function mandelbrot(width, height, maxIterations) {
  const xmin = -2.0;
  const xmax = 1.0;
  const ymin = -1.5;
  const ymax = 1.5;

  const dx = (xmax - xmin) / (width - 1);
  const dy = (ymax - ymin) / (height - 1);

  const startTime = performance.now(); // Start the timer

  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      let zx = xmin + x * dx;
      let zy = ymin + y * dy;
      const cx = zx;
      const cy = zy;

      let iteration = 0;
      while (iteration < maxIterations && zx * zx + zy * zy < 4.0) {
        const newZx = zx * zx - zy * zy + cx;
        const newZy = 2.0 * zx * zy + cy;
        zx = newZx;
        zy = newZy;
        iteration++;
      }
      
    }
  }

  const endTime = performance.now(); // Stop the timer
  const elapsedTime = endTime - startTime;
  const formattedTime = elapsedTime.toFixed(2);
  console.log(`${formattedTime}`);
}

// Set the desired parameters
const width = 80;
const height = 60;
const maxIterations = 1000;

// Call the mandelbrot function
mandelbrot(width, height, maxIterations);

  