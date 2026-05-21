from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database import get_db
from app import models, schemas

router = APIRouter(prefix="/execucoes", tags=["Execuções de Treino"])


@router.post("/", response_model=schemas.ExecucaoTreinoResponse)
def criar_execucao(execucao: schemas.ExecucaoTreinoCreate, db: Session = Depends(get_db)):
    exercicio = db.query(models.Exercicio).filter(
        models.Exercicio.id == execucao.exercicio_id
    ).first()

    if exercicio is None:
        raise HTTPException(status_code=404, detail="Exercício não encontrado")

    treino = db.query(models.Treino).filter(
        models.Treino.id == execucao.treino_id
    ).first()

    if treino is None:
        raise HTTPException(status_code=404, detail="Treino não encontrado")

    nova_execucao = models.ExecucaoTreino(
        data_execucao=execucao.data_execucao,
        carga=execucao.carga,
        observacao=execucao.observacao,
        exercicio_id=execucao.exercicio_id,
        treino_id=execucao.treino_id
    )

    db.add(nova_execucao)
    db.commit()
    db.refresh(nova_execucao)

    return nova_execucao


@router.get("/", response_model=list[schemas.ExecucaoTreinoResponse])
def listar_execucoes(db: Session = Depends(get_db)):
    return db.query(models.ExecucaoTreino).all()


@router.get("/{execucao_id}", response_model=schemas.ExecucaoTreinoResponse)
def buscar_execucao(execucao_id: int, db: Session = Depends(get_db)):
    execucao = db.query(models.ExecucaoTreino).filter(
        models.ExecucaoTreino.id == execucao_id
    ).first()

    if execucao is None:
        raise HTTPException(status_code=404, detail="Execução não encontrada")

    return execucao


@router.put("/{execucao_id}", response_model=schemas.ExecucaoTreinoResponse)
def atualizar_execucao(
    execucao_id: int,
    dados_execucao: schemas.ExecucaoTreinoCreate,
    db: Session = Depends(get_db)
):
    execucao = db.query(models.ExecucaoTreino).filter(
        models.ExecucaoTreino.id == execucao_id
    ).first()

    if execucao is None:
        raise HTTPException(status_code=404, detail="Execução não encontrada")

    exercicio = db.query(models.Exercicio).filter(
        models.Exercicio.id == dados_execucao.exercicio_id
    ).first()

    if exercicio is None:
        raise HTTPException(status_code=404, detail="Exercício não encontrado")

    treino = db.query(models.Treino).filter(
        models.Treino.id == dados_execucao.treino_id
    ).first()

    if treino is None:
        raise HTTPException(status_code=404, detail="Treino não encontrado")

    execucao.data_execucao = dados_execucao.data_execucao
    execucao.carga = dados_execucao.carga
    execucao.observacao = dados_execucao.observacao
    execucao.exercicio_id = dados_execucao.exercicio_id
    execucao.treino_id = dados_execucao.treino_id

    db.commit()
    db.refresh(execucao)

    return execucao


@router.delete("/{execucao_id}")
def excluir_execucao(execucao_id: int, db: Session = Depends(get_db)):
    execucao = db.query(models.ExecucaoTreino).filter(
        models.ExecucaoTreino.id == execucao_id
    ).first()

    if execucao is None:
        raise HTTPException(status_code=404, detail="Execução não encontrada")

    db.delete(execucao)
    db.commit()

    return {"mensagem": "Execução excluída com sucesso"}