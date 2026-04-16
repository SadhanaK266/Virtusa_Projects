import requests
from datetime import datetime

API_KEY = "0e37ee31393f7e8c956beb6a3bdda82c"

def fetch_weather(city):
    url = f"http://api.openweathermap.org/data/2.5/weather?q={city}&appid={API_KEY}&units=metric"

    response = requests.get(url)
    data = response.json()

    if data["cod"] != 200:
        return None

    weather_data = {
        "date": datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        "temperature": data["main"]["temp"],
        "humidity": data["main"]["humidity"],
        "city": city
    }

    return weather_data