from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database import get_db
from app import models, schemas

router = APIRouter(prefix="/usuarios", tags=["Usuários"])


@router.post("/", response_model=schemas.UsuarioResponse)
def criar_usuario(usuario: schemas.UsuarioCreate, db: Session = Depends(get_db)):
    email_existente = db.query(models.Usuario).filter(
        models.Usuario.email == usuario.email
    ).first()

    if email_existente is not None:
        raise HTTPException(status_code=400, detail="E-mail já cadastrado")

    novo_usuario = models.Usuario(
        nome=usuario.nome,
        email=usuario.email,
        senha=usuario.senha,
        peso=usuario.peso,
        altura=usuario.altura,
        meta=usuario.meta
    )

    db.add(novo_usuario)
    db.commit()
    db.refresh(novo_usuario)

    return novo_usuario


@router.post("/login", response_model=schemas.UsuarioResponse)
def login(dados_login: schemas.LoginRequest, db: Session = Depends(get_db)):
    usuario = db.query(models.Usuario).filter(
        models.Usuario.email == dados_login.email,
        models.Usuario.senha == dados_login.senha
    ).first()

    if usuario is None:
        raise HTTPException(status_code=401, detail="E-mail ou senha inválidos")

    return usuario


@router.get("/", response_model=list[schemas.UsuarioResponse])
def listar_usuarios(db: Session = Depends(get_db)):
    return db.query(models.Usuario).all()


@router.get("/{usuario_id}", response_model=schemas.UsuarioResponse)
def buscar_usuario(usuario_id: int, db: Session = Depends(get_db)):
    usuario = db.query(models.Usuario).filter(models.Usuario.id == usuario_id).first()

    if usuario is None:
        raise HTTPException(status_code=404, detail="Usuário não encontrado")

    return usuario


@router.put("/{usuario_id}", response_model=schemas.UsuarioResponse)
def atualizar_usuario(
    usuario_id: int,
    dados_usuario: schemas.UsuarioCreate,
    db: Session = Depends(get_db)
):
    usuario = db.query(models.Usuario).filter(models.Usuario.id == usuario_id).first()

    if usuario is None:
        raise HTTPException(status_code=404, detail="Usuário não encontrado")

    usuario.nome = dados_usuario.nome
    usuario.email = dados_usuario.email
    usuario.senha = dados_usuario.senha
    usuario.peso = dados_usuario.peso
    usuario.altura = dados_usuario.altura
    usuario.meta = dados_usuario.meta

    db.commit()
    db.refresh(usuario)

    return usuario


@router.delete("/{usuario_id}")
def excluir_usuario(usuario_id: int, db: Session = Depends(get_db)):
    usuario = db.query(models.Usuario).filter(models.Usuario.id == usuario_id).first()

    if usuario is None:
        raise HTTPException(status_code=404, detail="Usuário não encontrado")

    db.delete(usuario)
    db.commit()

    return {"mensagem": "Usuário excluído com sucesso"}