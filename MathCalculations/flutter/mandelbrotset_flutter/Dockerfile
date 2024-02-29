# Use the official Flutter image as the base image
FROM cirrusci/flutter:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the entire app to the container
COPY . .

# Install app dependencies
RUN flutter pub get

# Command to run the application
CMD ["flutter", "run"]

