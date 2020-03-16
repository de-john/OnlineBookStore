from requests import get
from bs4 import BeautifulSoup

url_input = input("Enter the url: ")
url = url_input
def get_data(page_number):
    response = get(url+ "?page="+ str(page_number))
    # print(response.text[:500])
    html_soup = BeautifulSoup(response.text, 'html.parser')
    # print("html_soup")
    print (len(html_soup.findAll("a", {"class": "part-cell"})))

page = 0

while True:
    try:
        page += 1
        get_data(page)
    except Exception as ex:
        print(ex)
        print("probably last page:", page)
        break # exit `while` loop

