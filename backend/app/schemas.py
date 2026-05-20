from datetime import date
from decimal import Decimal
from pydantic import BaseModel


class UsuarioBase(BaseModel):
    nome: str
    peso: Decimal | None = None
    altura: Decimal | None = None
    meta: str | None = None


class UsuarioCreate(UsuarioBase):
    pass


class UsuarioResponse(UsuarioBase):
    id: int

    class Config:
        from_attributes = True


class GrupoMuscularBase(BaseModel):
    nome: str


class GrupoMuscularCreate(GrupoMuscularBase):
    pass


class GrupoMuscularResponse(GrupoMuscularBase):
    id: int

    class Config:
        from_attributes = True


class ExercicioBase(BaseModel):
    nome: str
    series: int
    repeticoes: int
    grupo_muscular_id: int


class ExercicioCreate(ExercicioBase):
    pass


class ExercicioResponse(ExercicioBase):
    id: int

    class Config:
        from_attributes = True


class TreinoBase(BaseModel):
    nome: str
    descricao: str | None = None
    usuario_id: int


class TreinoCreate(TreinoBase):
    pass


class TreinoResponse(TreinoBase):
    id: int

    class Config:
        from_attributes = True


class ExecucaoTreinoBase(BaseModel):
    data_execucao: date
    carga: Decimal
    observacao: str | None = None
    exercicio_id: int
    treino_id: int


class ExecucaoTreinoCreate(ExecucaoTreinoBase):
    pass


class ExecucaoTreinoResponse(ExecucaoTreinoBase):
    id: int

    class Config:
        from_attributes = True