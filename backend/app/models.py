from sqlalchemy import Column, Integer, String, DECIMAL, Text, Date, ForeignKey
from sqlalchemy.orm import relationship

from app.database import Base


class Usuario(Base):
    __tablename__ = "usuario"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    email = Column(String(100), nullable=False)
    senha = Column(String(100), nullable=False)
    peso = Column(DECIMAL(5, 2))
    altura = Column(DECIMAL(4, 2))
    meta = Column(String(50))

    treinos = relationship("Treino", back_populates="usuario")


class GrupoMuscular(Base):
    __tablename__ = "grupo_muscular"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)

    exercicios = relationship("Exercicio", back_populates="grupo_muscular")


class Exercicio(Base):
    __tablename__ = "exercicio"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    series = Column(Integer, nullable=False)
    repeticoes = Column(Integer, nullable=False)
    grupo_muscular_id = Column(Integer, ForeignKey("grupo_muscular.id"), nullable=False)

    grupo_muscular = relationship("GrupoMuscular", back_populates="exercicios")
    execucoes = relationship("ExecucaoTreino", back_populates="exercicio")


class Treino(Base):
    __tablename__ = "treino"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    descricao = Column(Text)
    usuario_id = Column(Integer, ForeignKey("usuario.id"), nullable=False)

    usuario = relationship("Usuario", back_populates="treinos")
    execucoes = relationship("ExecucaoTreino", back_populates="treino")


class ExecucaoTreino(Base):
    __tablename__ = "execucao_treino"

    id = Column(Integer, primary_key=True, index=True)
    data_execucao = Column(Date, nullable=False)
    carga = Column(DECIMAL(6, 2), nullable=False)
    observacao = Column(Text)
    exercicio_id = Column(Integer, ForeignKey("exercicio.id"), nullable=False)
    treino_id = Column(Integer, ForeignKey("treino.id"), nullable=False)

    exercicio = relationship("Exercicio", back_populates="execucoes")
    treino = relationship("Treino", back_populates="execucoes")