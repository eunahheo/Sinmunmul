# 
FROM python:3.7.9

# 
WORKDIR /code

# 
RUN pip install -r requirements.txt
COPY ./requirements.txt /code/requirements.txt

# 
RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

# 
COPY ./app /code/app

# 
CMD ["uvicorn", "app.main:app", "--host", "j6a406.p.ssafy.io", "--port", "3031"]
