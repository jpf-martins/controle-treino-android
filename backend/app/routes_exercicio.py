from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database import get_db
from app import models, schemas

router = APIRouter(prefix="/exercicios", tags=["Exercícios"])


@router.post("/", response_model=schemas.ExercicioResponse)
def criar_exercicio(exercicio: schemas.ExercicioCreate, db: Session = Depends(get_db)):
    grupo = db.query(models.GrupoMuscular).filter(
        models.GrupoMuscular.id == exercicio.grupo_muscular_id
    ).first()

    if grupo is None:
        raise HTTPException(status_code=404, detail="Grupo muscular não encontrado")

    novo_exercicio = models.Exercicio(
        nome=exercicio.nome,
        series=exercicio.series,
        repeticoes=exercicio.repeticoes,
        grupo_muscular_id=exercicio.grupo_muscular_id
    )

    db.add(novo_exercicio)
    db.commit()
    db.refresh(novo_exercicio)

    return novo_exercicio


@router.get("/", response_model=list[schemas.ExercicioResponse])
def listar_exercicios(db: Session = Depends(get_db)):
    return db.query(models.Exercicio).all()


@router.get("/{exercicio_id}", response_model=schemas.ExercicioResponse)
def buscar_exercicio(exercicio_id: int, db: Session = Depends(get_db)):
    exercicio = db.query(models.Exercicio).filter(
        models.Exercicio.id == exercicio_id
    ).first()

    if exercicio is None:
        raise HTTPException(status_code=404, detail="Exercício não encontrado")

    return exercicio


@router.put("/{exercicio_id}", response_model=schemas.ExercicioResponse)
def atualizar_exercicio(
    exercicio_id: int,
    dados_exercicio: schemas.ExercicioCreate,
    db: Session = Depends(get_db)
):
    exercicio = db.query(models.Exercicio).filter(
        models.Exercicio.id == exercicio_id
    ).first()

    if exercicio is None:
        raise HTTPException(status_code=404, detail="Exercício não encontrado")

    grupo = db.query(models.GrupoMuscular).filter(
        models.GrupoMuscular.id == dados_exercicio.grupo_muscular_id
    ).first()

    if grupo is None:
        raise HTTPException(status_code=404, detail="Grupo muscular não encontrado")

    exercicio.nome = dados_exercicio.nome
    exercicio.series = dados_exercicio.series
    exercicio.repeticoes = dados_exercicio.repeticoes
    exercicio.grupo_muscular_id = dados_exercicio.grupo_muscular_id

    db.commit()
    db.refresh(exercicio)

    return exercicio


@router.delete("/{exercicio_id}")
def excluir_exercicio(exercicio_id: int, db: Session = Depends(get_db)):
    exercicio = db.query(models.Exercicio).filter(
        models.Exercicio.id == exercicio_id
    ).first()

    if exercicio is None:
        raise HTTPException(status_code=404, detail="Exercício não encontrado")

    db.delete(exercicio)
    db.commit()

    return {"mensagem": "Exercício excluído com sucesso"}