# Python 크롤러

## Tech Stack

- Python 3.8.10 << Dependency로 Pycurl을 설치할 떄 Windows상에서는 3.8.10버전 이외에서는 에러가 발생하므로 주의하시기 바랍니다.
- scrapy: 웹 크롤링을 위한 라이브러리 [Scrapy Documentation](https://docs.scrapy.org/en/latest/)

## 크롤러 실행
settings.py가 존재하는 루트에서 코드를 실행하여 크롤링을 시작합니다.
```bash
$ scrapy crawl naverNewsCrawling
```

## 이슈사항

- 네이버 페이지의 경우 UTF-8인코딩이 아니라 EUC-KR이기에 인코딩을 변경해야 합니다.