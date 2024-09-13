@echo off
setlocal

:: Set the base directory to the current directory
set "BASE_DIR=%cd%"

:: Show the path and ask if the user wants to proceed
echo This script will generate the folder structure in: %BASE_DIR%
set /p confirmation="Do you want to proceed? (y/n): "

:: Check if the user wants to proceed
if /i "%confirmation%"=="y" (
    echo Creating Spring Boot REST API folder structure...

    :: Create the folder structure
    mkdir %BASE_DIR%\config
    mkdir %BASE_DIR%\controller
    mkdir %BASE_DIR%\dto
    mkdir %BASE_DIR%\exception
    mkdir %BASE_DIR%\model
    mkdir %BASE_DIR%\repository
    mkdir %BASE_DIR%\service
    mkdir %BASE_DIR%\mapper
    mkdir %BASE_DIR%\security
    mkdir %BASE_DIR%\util

    echo Spring Boot REST API folder structure generated successfully!
) else (
    echo Operation canceled.
)
