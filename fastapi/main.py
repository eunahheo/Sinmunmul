import uvicorn
from fastapi import FastAPI, Request
from starlette.middleware.cors import CORSMiddleware
from app.common.config import conf
from app.routes import news


def create_app():
    """
    앱 함수 실행
    :return:
    """
    c = conf()
    app = FastAPI(title="API_NAME",
              description="API_DESC",
              version="0.2.0",
              docs_url='/fapi/docs',
              redoc_url='/fapi/redoc',
              openapi_url='/fapi/openapi.json')


    # 미들웨어
    app.add_middleware(
        CORSMiddleware,
        allow_origins=conf().ALLOW_SITE,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )

    # 라우터
    app.include_router(news.router)

    return app


app = create_app()

if __name__ == "__main__":
    uvicorn.run("app.main:app", host="0.0.0.0", port=3031, reload=True)


@app.get("/fapi/newsbig/sinmunmul")
def read_main(request: Request):
    return {"message": "안녕하세요! 뉴스빅의 신문물입니다!", "root_path": request.scope.get("root_path")}
