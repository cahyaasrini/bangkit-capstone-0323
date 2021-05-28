from flask import Flask, request, jsonify
import json 

app = Flask(__name__)

@app.route('/medicine', methods=['GET'])

def all_medicine():
    filename = 'bangkit_0323_dataset.json'
    with open(filename) as f: 
        file = json.load(f) 
    return file

app.run()