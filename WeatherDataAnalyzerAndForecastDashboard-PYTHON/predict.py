import pandas as pd
from sklearn.linear_model import LinearRegression
import numpy as np

FILE_PATH = "data/weather_data.csv"

def predict_temperature():
    # Load data
    df = pd.read_csv(FILE_PATH)

    # Convert date to numeric (important for ML)
    df['date'] = pd.to_datetime(df['date'])
    df['timestamp'] = df['date'].map(pd.Timestamp.timestamp)

    # Prepare data
    X = df[['timestamp']]   # input
    y = df['temperature']   # output

    # Train model
    model = LinearRegression()
    model.fit(X, y)

    # Predict next time (future)
    next_time = df['timestamp'].max() + 3600  # +1 hour

    predicted_temp = model.predict([[next_time]])

    return predicted_temp[0]


# Test run
if __name__ == "__main__":
    result = predict_temperature()
    print(f"Predicted Temperature (next hour): {result:.2f} °C")