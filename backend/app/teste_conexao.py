from database import engine
from sqlalchemy import text

try:
    with engine.connect() as conexao:
        resultado = conexao.execute(text("SELECT 1"))
        print("Conexão com MySQL funcionando!")
except Exception as erro:
    print("Erro ao conectar no MySQL:")
    print(erro)