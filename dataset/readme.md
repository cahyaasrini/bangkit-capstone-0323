## Data Source 

The source of data for **medy** is [openFDA](https://open.fda.gov/). ((cont))

## Data Collection 

We collect the [Human Drug Label dataset](https://open.fda.gov/data/downloads/) by downloading the whole dataset that consist of 10 json files and do a data preparation as follows: 

1. Data Filtering

   From the whole dataset, we only need the [OTC / over-the-counter](https://www.fda.gov/drugs/information-consumers-and-patients-drugs/otc-drug-facts-label) type of drug products. So, we filter the OTC drugs and come up with **a raw dataset consist of 65670 records of Human OTC Drug Labels** in [raw-fda-otc.json](https://drive.google.com/drive/folders/1NuOK6hWEDek11kFARszu9K9O8icySx_I?usp=sharing). The code is in the [part-1-data-filtering.ipynb](https://github.com/cahyaasrini/bangkit-medy/blob/main/dataset/part-1-data-filtering.ipynb) notebook. 

2. Data Cleansing 
  
  ((cont))


