from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database import get_db
from app import models, schemas

router = APIRouter(prefix="/grupos", tags=["Grupos Musculares"])


@router.post("/", response_model=schemas.GrupoMuscularResponse)
def criar_grupo(grupo: schemas.GrupoMuscularCreate, db: Session = Depends(get_db)):
    novo_grupo = models.GrupoMuscular(
        nome=grupo.nome
    )

    db.add(novo_grupo)
    db.commit()
    db.refresh(novo_grupo)

    return novo_grupo


@router.get("/", response_model=list[schemas.GrupoMuscularResponse])
def listar_grupos(db: Session = Depends(get_db)):
    return db.query(models.GrupoMuscular).all()


@router.get("/{grupo_id}", response_model=schemas.GrupoMuscularResponse)
def buscar_grupo(grupo_id: int, db: Session = Depends(get_db)):
    grupo = db.query(models.GrupoMuscular).filter(models.GrupoMuscular.id == grupo_id).first()

    if grupo is None:
        raise HTTPException(status_code=404, detail="Grupo muscular não encontrado")

    return grupo


@router.put("/{grupo_id}", response_model=schemas.GrupoMuscularResponse)
def atualizar_grupo(
    grupo_id: int,
    dados_grupo: schemas.GrupoMuscularCreate,
    db: Session = Depends(get_db)
):
    grupo = db.query(models.GrupoMuscular).filter(models.GrupoMuscular.id == grupo_id).first()

    if grupo is None:
        raise HTTPException(status_code=404, detail="Grupo muscular não encontrado")

    grupo.nome = dados_grupo.nome

    db.commit()
    db.refresh(grupo)

    return grupo


@router.delete("/{grupo_id}")
def excluir_grupo(grupo_id: int, db: Session = Depends(get_db)):
    grupo = db.query(models.GrupoMuscular).filter(models.GrupoMuscular.id == grupo_id).first()

    if grupo is None:
        raise HTTPException(status_code=404, detail="Grupo muscular não encontrado")

    db.delete(grupo)
    db.commit()

    return {"mensagem": "Grupo muscular excluído com sucesso"}