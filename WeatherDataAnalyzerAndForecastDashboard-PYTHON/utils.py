import csv
import os

FILE_PATH = "data/weather_data.csv"

def save_to_csv(weather_data):
    file_exists = os.path.isfile(FILE_PATH)

    with open(FILE_PATH, mode='a', newline='') as file:
        writer = csv.DictWriter(file, fieldnames=["date", "temperature", "humidity"])

        # Write header only once
        if not file_exists:
            writer.writeheader()

        writer.writerow(weather_data)