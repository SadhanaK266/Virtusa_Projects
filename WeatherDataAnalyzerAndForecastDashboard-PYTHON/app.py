from flask import Flask, render_template, request
from fetch_weather import fetch_weather
from analysis import generate_chart
from predict import predict_temperature

app = Flask(__name__)

@app.route("/", methods=["GET"])
def home():
    city = request.args.get("city", "Coimbatore")  # default

    weather = fetch_weather(city)

    if weather:
        generate_chart()
        prediction = predict_temperature()
    else:
        prediction = None

    return render_template(
        "index.html",
        weather=weather,
        prediction=prediction,
        city=city
    )

if __name__ == "__main__":
    app.run(debug=True)