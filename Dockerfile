# Use an official Python runtime as a parent image
FROM python:3.9-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the requirements.txt into the container
COPY app/requirements.txt /app/requirements.txt

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application files into the container
COPY . /app

# Make port 5000 available to the world outside the container
EXPOSE 5000

# Define environment variable
ENV NAME World

# Run the application when the container launches
CMD ["python", "app.py"]
