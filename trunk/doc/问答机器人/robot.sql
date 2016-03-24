--会话记录表
TRUNCATE TABLE QA_SESSION;
DROP TABLE QA_SESSION;
CREATE TABLE QA_SESSION (
    ID         VARCHAR2(16) CONSTRAINT PK_QA_SESSION PRIMARY KEY USING INDEX TABLESPACE EDU_INDEX,
    SYSTEM_ID  VARCHAR2(16),
    TYPE       VARCHAR2(2) NOT NULL,
    START_TIME DATE NOT NULL,
    END_TIME   DATE,
    Q_USER_ID  VARCHAR2(32),
    Q_USER_IP  VARCHAR2(32),
    A_USER_ID  VARCHAR2(32)
);

--问答日志表
TRUNCATE TABLE QA_LOG;
DROP TABLE QA_LOG;
CREATE TABLE QA_LOG (
    ID          VARCHAR2(16) CONSTRAINT PK_QA_LOG PRIMARY KEY USING INDEX TABLESPACE EDU_INDEX,
    SESSION_ID  VARCHAR2(16) NOT NULL,
    Q_TYPE      VARCHAR2(2) NOT NULL,
    Q           VARCHAR2(100) NOT NULL,
    A_TYPE      VARCHAR2(2),
    CREATE_TIME DATE NOT NULL
);

--回复日志表
TRUNCATE TABLE A_LOG;
DROP TABLE A_LOG;
CREATE TABLE A_LOG (
    ID          VARCHAR2(16) CONSTRAINT PK_A_LOG PRIMARY KEY USING INDEX TABLESPACE EDU_INDEX,
    QA_LOG_ID   VARCHAR2(16) NOT NULL,
    CMS_ID      VARCHAR2(16) NOT NULL,
    CMS_VERSION VARCHAR2(16)
);

--机器人问答配置表
TRUNCATE TABLE ROBOT_Q_SET;
DROP TABLE ROBOT_Q_SET;
CREATE TABLE ROBOT_Q_SET (
    ID          VARCHAR2(16) CONSTRAINT PK_ROBOT_Q_SET PRIMARY KEY USING INDEX TABLESPACE EDU_INDEX,
    Q           VARCHAR2(100) NOT NULL
);

--机器人问答配置表
TRUNCATE TABLE ROBOT_A_SET;
DROP TABLE ROBOT_A_SET;
CREATE TABLE ROBOT_A_SET (
    ID          VARCHAR2(16) CONSTRAINT PK_ROBOT_A_SET PRIMARY KEY USING INDEX TABLESPACE EDU_INDEX,
    Q_ID        VARCHAR2(16),
    A           VARCHAR2(200) NOT NULL
);