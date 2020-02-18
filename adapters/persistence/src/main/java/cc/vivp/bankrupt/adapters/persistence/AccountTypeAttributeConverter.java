package cc.vivp.bankrupt.adapters.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import cc.vivp.bankrupt.domain.AccountType;

@Converter
class AccountTypeAttributeConverter implements AttributeConverter<AccountType, String> {

  @Override
  public String convertToDatabaseColumn(AccountType attribute) {
    return attribute.name();
  }

  @Override
  public AccountType convertToEntityAttribute(String dbData) {
    return AccountType.valueOf(dbData);
  }
}
