import pandas as pd
import matplotlib.pyplot as plt

FILE_PATH = "data/weather_data.csv"
CHART_PATH = "static/charts.png"

def generate_chart():
    # Read CSV
    df = pd.read_csv(FILE_PATH)

    # Convert date column to datetime
    df['date'] = pd.to_datetime(df['date'])

    # Sort by date (important)
    df = df.sort_values(by='date')

    # Plot graph
    plt.figure(figsize=(10,5))

    plt.plot(df['date'], df['temperature'], marker='o', label='Temperature (°C)')
    plt.plot(df['date'], df['humidity'], marker='x', label='Humidity (%)')

    plt.xlabel("Date & Time")
    plt.ylabel("Values")
    plt.title("Weather Trends")
    plt.legend()

    plt.xticks(rotation=45)
    plt.tight_layout()

    # Save chart
    plt.savefig(CHART_PATH)

    # Optional: show graph (for testing)
    # plt.show()


# Test run
if __name__ == "__main__":
    generate_chart()