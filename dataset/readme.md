## About the dataset 

We use Human Drug Label data from [openFDA](https://open.fda.gov/). 

## How we get it 

The whole Human Drug Label data from openFDA can be found [here](https://open.fda.gov/data/downloads/). We only download the first part of the whole data (there are 10 parts) to take samples for our project. It's a json file, consist of 20k records of Human Drug Label.

## How we take samples 

From 20k records, we filter it based on our needs (and limitations):   

1. We only use [Human OTC Drugs](https://en.wikipedia.org/wiki/Over-the-counter_drug) because it doesn't need prescriptions. 
2. For simplification, we only take drugs for common symptoms with proper purpose & indications. 
   So, we end up taking samples of 
   - **acne** (104 records), 
   - **cough** (390 records), and 
   - **diarrhea** (69 records) 
   with the total of 563. 

The notebook is provided along with final dataset we use for this project. 

