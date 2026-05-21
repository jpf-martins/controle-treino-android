from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database import get_db
from app import models, schemas

router = APIRouter(prefix="/treinos", tags=["Treinos"])


@router.post("/", response_model=schemas.TreinoResponse)
def criar_treino(treino: schemas.TreinoCreate, db: Session = Depends(get_db)):
    usuario = db.query(models.Usuario).filter(
        models.Usuario.id == treino.usuario_id
    ).first()

    if usuario is None:
        raise HTTPException(status_code=404, detail="Usuário não encontrado")

    novo_treino = models.Treino(
        nome=treino.nome,
        descricao=treino.descricao,
        usuario_id=treino.usuario_id
    )

    db.add(novo_treino)
    db.commit()
    db.refresh(novo_treino)

    return novo_treino


@router.get("/", response_model=list[schemas.TreinoResponse])
def listar_treinos(db: Session = Depends(get_db)):
    return db.query(models.Treino).all()


@router.get("/{treino_id}", response_model=schemas.TreinoResponse)
def buscar_treino(treino_id: int, db: Session = Depends(get_db)):
    treino = db.query(models.Treino).filter(
        models.Treino.id == treino_id
    ).first()

    if treino is None:
        raise HTTPException(status_code=404, detail="Treino não encontrado")

    return treino


@router.put("/{treino_id}", response_model=schemas.TreinoResponse)
def atualizar_treino(
    treino_id: int,
    dados_treino: schemas.TreinoCreate,
    db: Session = Depends(get_db)
):
    treino = db.query(models.Treino).filter(
        models.Treino.id == treino_id
    ).first()

    if treino is None:
        raise HTTPException(status_code=404, detail="Treino não encontrado")

    usuario = db.query(models.Usuario).filter(
        models.Usuario.id == dados_treino.usuario_id
    ).first()

    if usuario is None:
        raise HTTPException(status_code=404, detail="Usuário não encontrado")

    treino.nome = dados_treino.nome
    treino.descricao = dados_treino.descricao
    treino.usuario_id = dados_treino.usuario_id

    db.commit()
    db.refresh(treino)

    return treino


@router.delete("/{treino_id}")
def excluir_treino(treino_id: int, db: Session = Depends(get_db)):
    treino = db.query(models.Treino).filter(
        models.Treino.id == treino_id
    ).first()

    if treino is None:
        raise HTTPException(status_code=404, detail="Treino não encontrado")

    db.delete(treino)
    db.commit()

    return {"mensagem": "Treino excluído com sucesso"}