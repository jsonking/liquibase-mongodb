package liquibase.ext.mongodb.statement;

/*-
 * #%L
 * Liquibase MongoDB Extension
 * %%
 * Copyright (C) 2019 Mastercard
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.nosql.statement.NoSqlQueryForLongStatement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.Document;

import java.util.List;

/**
 * Queries the database for the number of collections that match the supplied collectionName
 * i.e returns 1 if the collection is present; else 0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CountCollectionByNameStatement extends AbstractCollectionStatement
        implements NoSqlQueryForLongStatement<MongoLiquibaseDatabase> {

    public CountCollectionByNameStatement(final String collectionName) {
        super(collectionName);
    }

    @Override
    public String getCommandName() {
        return ListCollectionNamesStatement.COMMAND_NAME;
    }

    @Override
    public long queryForLong(final MongoLiquibaseDatabase database) {
        Document filter = new Document("name", getCollectionName());
        ListCollectionNamesStatement statement = new ListCollectionNamesStatement(filter);
        return statement.queryForList(database).size();
    }
}
