import csv 
import requests 
from bs4 import BeautifulSoup
import pandas as pd

def get_soup(url): 
  page = requests.get(url)
  soup = BeautifulSoup(page.content, 'html.parser')
  return soup 

def get_med_data(med_url): 
  try: 
    soup = get_soup(med_url)

    if 'Apotek Online' in str(soup.title): # skip any url that redirect to home 
      return None
    else: 
      try: 
        med_title = soup.find_all("h1", {"class": "health-insurance__ttl"})[0].text
        med_title = med_title.strip('\n')
      except:
        med_title = 'empty'
      # finally:
      #   print(med_title)

      try:   
        med_ctg = soup.find_all('div', {'class': 'breadcrumbs'})[0].find_all('a')[1].text.strip()
      except: 
        med_ctg = 'empty'
      # finally:
      #   print(med_ctg)

      try: 
        med_img = 'https://hdmall.id' + soup.find_all('div', {"class": "health-insurance__slider"})[0].find('source')['data-srcset']
        # print(med_img)
      except:
        med_img = 'empty'
      # finally:
        # print(med_img)

      try:
        med_desc =  soup.find_all('div', {"class": "package__features-description"})[0].text.strip()
      except: 
        med_desc = 'empty' 
      # finally:
        # print(med_desc)

      try:
        details = soup.find_all('div', {'class': 'fr-view health-insurance__details'})[0].find_all(['h1', 'h2', 'h3', 'h4', 'p', 'li', 'ul'])
        # if we include the tags, we need to add more tag like div' and 'span' 
        # we actually can do it without defining the tags, but if we don't do it the text will have no space between another that makes preprocessing more tricky. 
        # tricky is no problem its just about the downside that we choose  
        
        med_details = ' / '.join([elm.text for elm in details])
      except: 
        med_details = 'empty'
      # finally:
        # print(med_details)  
        
      return med_title, med_ctg, med_img, med_desc, med_details 
  except:
    return None
    
filename = 'meds.csv'
fields = ['no', 'url', 'id', 'name', 'title', 'category', 'image', 'description', 'details']

with open(filename, 'w', encoding="utf-8") as csvfile: 
  csv_writer = csv.writer(csvfile)
  csv_writer.writerow(fields)  

  page_start = 1 
  page_total = 182 
  med_num = 1
  list_id = [] 

  for page_num in range(page_start, page_total+1):  
      page_url = 'https://hdmall.id/sitemap/apotik-online?page=' + str(page_num)
      print('page', page_num, ':', page_url)

      soup = get_soup(page_url) 

      for elm in soup.find_all("a"): 
        a_href = elm['href'] 
        a_text = elm.text 

        if ('/apotik-online/' in a_href) and ('sitemap' not in a_href) and ('hdmall' not in a_href) and ('Obat' not in a_text): # filtering irrelevant content 
          med_url = 'https://hdmall.id' + a_href
          med_id = a_href.split('/')[-1]
          med_name = a_text  
    
          if med_id not in list_id: # filtering duplicate data 
            try: 
              print(med_num, med_url)
              med_title, med_ctg, med_img, med_desc, med_details = get_med_data(med_url)
              med_data = [med_num, med_url, med_id, med_name, med_title, med_ctg, med_img, med_desc, med_details]
              # print(med_data)

              csv_writer.writerow(med_data) # write to file
              list_id.append(med_id) 
              print('done.')

              med_num += 1
            except: 
              print('skip. url redirect to home or else.')
              
          else: 
            print('Duplicate:', med_id, med_url, 'on page', page_num)
