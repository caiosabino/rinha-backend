#!/bin/bash
# Arquivos de configuração a serem verificados
quantity_secret_in_file="./src/main/resources/application.yml"

# Quantidade de SECRET_ que é esperado
expected_count=0

check_file() {
  local file=$1
  local all_patterns_present=true

  if [[ -f "$file" ]]; then
    # Verifica se pelo menos um padrão está ausente
    for pattern in "${patterns[@]}"; do
      if ! grep -q "$pattern" "$file"; then
        echo "O arquivo '$file' não contém o padrão '$pattern'."
        all_patterns_present=false
      fi
    done

    if [ "$all_patterns_present" = false ]; then
      echo "O arquivo '$file' contém dados sensíveis. Verifique antes de fazer o commit: ${patterns[*]}."
      exit 1
    fi
  else
    echo "Aviso: O arquivo '$file' não foi encontrado."
    exit 1
  fi

  return 0
}

check_file_quantity() {
  local file=$1
  local pattern=$2
  local expected_count=$3

  if [[ -f "$file" ]]; then
    # Conta o número de ocorrências do padrão no arquivo
    count=$(grep -o "$pattern" "$file" | wc -l)

    if [ "$count" -eq "$expected_count" ]; then
      exit 0
    elif [ "$count" -lt "$expected_count" ]; then
      echo "O arquivo '$file' contém $count ocorrências do padrão '$pattern', que é menor que o esperado: ($expected_count), pode conter dados sensíveis. Verifique antes de fazer o commit."
      exit 1
    else
      echo "O arquivo '$file' contém $count ocorrências do padrão '$pattern', que é maior que o esperado ($expected_count), por favor adicionar a nova SECRET na validacao do pre commit."
      exit 1
    fi
  else
    echo "Aviso: O arquivo '$file' não foi encontrado."
    exit 1
  fi
}

# Inicializa o status de erro
status=0

# Verifica cada arquivo
for file in "${files[@]}"; do
  check_file "$file"
  if [ $? -ne 0 ]; then
    status=1
  fi
done

# Verifica a quantidade no arquivo específico
check_file_quantity "$quantity_secret_in_file" "$specific_pattern" "$expected_count"
if [ $? -ne 0 ]; then
  status=1
fi

# Retorna o status geral
if [ $status -eq 1 ]; then
  exit 1
else
  echo "Todos os arquivos passaram na verificação."
  exit 0
fi
