#!/bin/bash

# Set the base directory as the current directory
BASE_DIR=$(pwd)

# Show the path and ask if the user wants to proceed
echo "This script will generate the folder structure in: $BASE_DIR"
read -p "Do you want to proceed? (y/n): " confirmation

# Check if the user wants to proceed
if [[ "$confirmation" == "y" || "$confirmation" == "Y" ]]; then
    echo "Creating Spring Boot REST API folder structure..."

    # Create the folder structure
    mkdir -p $BASE_DIR/{config,controller,dto,exception,model,repository,service,mapper,security,util}

    echo "Spring Boot REST API folder structure generated successfully!"
else
    echo "Operation canceled."
fi
