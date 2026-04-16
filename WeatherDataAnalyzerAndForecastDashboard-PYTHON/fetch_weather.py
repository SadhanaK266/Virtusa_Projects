import requests
from datetime import datetime
import os
from dotenv import load_dotenv

load_dotenv()

API_KEY = os.getenv("API_KEY")

def fetch_weather(city):
    if not API_KEY:
        raise ValueError("API_KEY not found. Please set it in .env file.")

    url = f"http://api.openweathermap.org/data/2.5/weather?q={city}&appid={API_KEY}&units=metric"

    response = requests.get(url)
    data = response.json()

    if data.get("cod") != 200:
        return None

    weather_data = {
        "date": datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        "temperature": data["main"]["temp"],
        "humidity": data["main"]["humidity"],
        "city": city
    }

    return weather_data


if __name__ == "__main__":
    result = fetch_weather("Chennai")
    print(result)