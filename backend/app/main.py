from fastapi import FastAPI

from app.routes_usuario import router as usuario_router

app = FastAPI()

app.include_router(usuario_router)


@app.get("/")
def inicio():
    return {"mensagem": "API do Controle de Treino funcionando"}