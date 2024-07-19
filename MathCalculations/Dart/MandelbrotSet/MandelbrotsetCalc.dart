void main() {
  final int width = 80;
  final int height = 60;
  final int maxIterations = 1000;

  final double xmin = -2.0;
  final double xmax = 1.0;
  final double ymin = -1.5;
  final double ymax = 1.5;

  final double dx = (xmax - xmin) / (width - 1);
  final double dy = (ymax - ymin) / (height - 1);

  final stopwatch = Stopwatch()..start();

  for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
      double zx = xmin + x * dx;
      double zy = ymin + y * dy;
      double cx = zx;
      double cy = zy;

      int iteration = 0;
      while (iteration < maxIterations && (zx * zx + zy * zy) < 4.0) {
        double newZx = zx * zx - zy * zy + cx;
        double newZy = 2.0 * zx * zy + cy;
        zx = newZx;
        zy = newZy;
        iteration++;
      }
    }
  }

  stopwatch.stop();
  double elapsedTime = stopwatch.elapsedMilliseconds.toDouble();
  String formattedTime = elapsedTime.toStringAsFixed(2);
  print(formattedTime);
}
