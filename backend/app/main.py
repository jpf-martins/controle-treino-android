from fastapi import FastAPI

from app.routes_usuario import router as usuario_router
from app.routes_grupoMuscular import router as grupo_muscular_router
from app.routes_exercicio import router as exercicio_router
from app.routes_treino import router as treino_router
from app.routes_execucao_treino import router as execucao_treino_router

app = FastAPI()

app.include_router(usuario_router)
app.include_router(grupo_muscular_router)
app.include_router(exercicio_router)
app.include_router(treino_router)
app.include_router(execucao_treino_router)


@app.get("/")
def inicio():
    return {"mensagem": "API do Controle de Treino funcionando"}