package com.hxb.common.dataSource;

public abstract interface NcsConnection {
	public abstract NcsStatement getNcsStatement();

	public abstract void commit();

	public abstract void setAutoCommit(boolean paramBoolean);

	public abstract void rollback();

	public abstract void free();

	public abstract boolean isClosed();

	public abstract void close();
}