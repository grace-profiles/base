hibernate:
    dialect: <% if (options['database'] == 'h2') { %>org.hibernate.dialect.H2Dialect<% } else if (options['database'] == 'mariadb') { %>org.hibernate.dialect.MariaDBDialect<% } else if (options['database'] == 'mysql') { %>org.hibernate.dialect.MySQLDialect<% } else if (options['database'] == 'postgresql') { %>org.hibernate.dialect.PostgreSQL10Dialect<% } else if (options['database'] == 'sqlserver') { %>org.hibernate.dialect.SQLServerDialect<% } else { %><% } %>
    cache:
        queries: false
        use_second_level_cache: false
        use_query_cache: false
dataSource:
    pooled: true
    driverClassName: <% if (options['database'] == 'h2') { %>org.h2.Driver<% } else if (options['database'] == 'mysql') { %>com.mysql.cj.jdbc.Driver<% } else if (options['database'] == 'mariadb') { %>org.mariadb.jdbc.Driver<% } else if (options['database'] == 'postgresql') { %>org.postgresql.Driver<% } else if (options['database'] == 'sqlserver') { %>com.microsoft.sqlserver.jdbc.SQLServerDriver<% } else { %><% } %>
    username: <% if (options['database'] == 'h2') { %>sa<% } else if (options['database'] == 'mysql') { %>root<% } else if (options['database'] == 'mariadb') { %>root<% } else if (options['database'] == 'postgresql') { %>postgres<% } else { %>sa<% } %>
    password: <% if (options['database'] == 'h2') { %>''<% } else if (options['database'] == 'mysql') { %>''<% } else if (options['database'] == 'mariadb') { %>''<% } else if (options['database'] == 'postgresql') { %>'postgres'<% } else { %>''<% } %>

environments:
    development:
        dataSource:
            dbCreate: create-drop
            url: <% if (options['database'] == 'h2') { %>jdbc:h2:mem:@grace.codegen.projectSnakeCaseName@_dev;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE<% } else if (options['database'] == 'mariadb') { %>jdbc:mariadb://localhost:3306/@grace.codegen.projectSnakeCaseName@_dev<% } else if (options['database'] == 'mysql') { %>jdbc:mysql://localhost:3306/@grace.codegen.projectSnakeCaseName@_dev<% } else if (options['database'] == 'postgresql') { %>jdbc:postgresql://localhost:5432/@grace.codegen.projectSnakeCaseName@_dev<% } else if (options['database'] == 'sqlserver') { %>jdbc:sqlserver://localhost:1433/@grace.codegen.projectSnakeCaseName@_dev<% } else { %><% } %>
    test:
        dataSource:
            dbCreate: update
            url: <% if (options['database'] == 'h2') { %>jdbc:h2:mem:@grace.codegen.projectSnakeCaseName@_test;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE<% } else if (options['database'] == 'mariadb') { %>jdbc:mariadb://localhost:3306/@grace.codegen.projectSnakeCaseName@_test<% } else if (options['database'] == 'mysql') { %>jdbc:mysql://localhost:3306/@grace.codegen.projectSnakeCaseName@_test<% } else if (options['database'] == 'postgresql') { %>jdbc:postgresql://localhost:5432/@grace.codegen.projectSnakeCaseName@_test<% } else if (options['database'] == 'sqlserver') { %>jdbc:sqlserver://localhost:1433/@grace.codegen.projectSnakeCaseName@_test<% } else { %><% } %>
    production:
        dataSource:
            dbCreate: none
            url: <% if (options['database'] == 'h2') { %>jdbc:h2:./@grace.codegen.projectSnakeCaseName@_prod;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE<% } else if (options['database'] == 'mariadb') { %>jdbc:mariadb://localhost:3306/@grace.codegen.projectSnakeCaseName@_prod<% } else if (options['database'] == 'mysql') { %>jdbc:mysql://localhost:3306/@grace.codegen.projectSnakeCaseName@_prod<% } else if (options['database'] == 'postgresql') { %>jdbc:postgresql://localhost:5432/@grace.codegen.projectSnakeCaseName@_prod<% } else if (options['database'] == 'sqlserver') { %>jdbc:sqlserver://localhost:1433/@grace.codegen.projectSnakeCaseName@_prod<% } else { %><% } %>
