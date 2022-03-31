#
FROM python:3.9

#
WORKDIR /fastapi

#
#COPY ./requirements.txt /fastapi/requirements.txt

#
RUN pip install --no-cache-dir --upgrade -r /fastapi/requirements.txt

#
COPY ./fastapi /fastapi

CMD ["uvicorn", "main:app", "--host", "j6a406.p.ssafy.io", "--port", "3031"]
