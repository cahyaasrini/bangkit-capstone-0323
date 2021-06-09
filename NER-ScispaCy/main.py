import scispacy 
import spacy
import pandas as pd 

model_name = 'en_core_sci_md'
# model_name = 'en_ner_bc5cdr_md'

nlp = spacy.load(model_name)

print('done;')
'''

filename = 'demo-drugs-v1.csv'
df = pd.read_csv(filename)

data = df.indications_and_usage.values

def extract_drug_entity(text):
    doc =  nlp(text)
    result = [ent.text for ent in list(doc.ents)]
    return result

col_entities = [', '.join(extract_drug_entity(text)) for text in data]

df['entities'] = col_entities

df.to_csv('demo-drugs-v1-result.csv')

'''