# AutoReplace (Fabric 26.2)

Mod de seleção de área (estilo Litematica) + quebra em massa.

## Como usar

1. Instale **Fabric Loader** para Minecraft **26.2**
2. Coloque **Fabric API** (`0.154.2+26.2` ou compatível) na pasta `mods`
3. Coloque o jar `autoreplace-1.0.0.jar` na pasta `mods`
4. No jogo, pressione **Z** para abrir o menu

### Menu (tecla Z)

| Botão | Função |
|-------|--------|
| **Ativado** | Liga/desliga o AutoReplace |
| **Modo Seleção** | Ativa seleção de área com botão direito |
| **Só Mesmo Bloco** | Quebra só blocos iguais ao que você quebrou |
| **Dropar Itens** | Preferência (a quebra usa o método vanilla do client) |
| **Limpar Seleção** | Apaga a área selecionada |
| **Fechar** | Fecha o menu |

### Fluxo

1. Pressione **Z** → clique em **Modo Seleção** (ON)
2. **Botão direito** no 1º canto da área
3. **Botão direito** no 2º canto (caixa cyan aparece)
4. Pressione **Z** → clique em **Ativado: SIM**
5. Quebre **1 bloco** dentro da área → todos os blocos iguais da área quebram

## Compilar

```bash
./gradlew build
```

O jar sai em `build/libs/autoreplace-1.0.0.jar`.

## Requisitos

- Minecraft 26.2
- Fabric Loader >= 0.19.3
- Fabric API
- Java 25 (para compilar)
