package com.allatori;

public class AnotherNameRepo {

	/* OK */

	private final AnotherNameRepoLow2 anotherNameRepoLow;
	private final NameRepository nameRepository;
	private final RenamingUtils renamingUtils;
	private final AnotherNameRepoField anotherNameRepoField;
	private final AnotherNameRepoLow1 nameRepositoryLow;

	public static NameRepository getNameRepository(AnotherNameRepo anotherNameRepo) {
		return anotherNameRepo.nameRepository;
	}

	public static AnotherNameRepoLow1 getNameRepositoryLow(AnotherNameRepo anotherNameRepo) {
		return anotherNameRepo.nameRepositoryLow;
	}

	private AnotherNameRepo(Renamer renamer) { /* Not used param */
		this.anotherNameRepoLow = new AnotherNameRepoLow2(this, null);
		this.nameRepositoryLow = new AnotherNameRepoLow1(this, null);
		this.nameRepository = new NameRepository(this, null);
		this.anotherNameRepoField = new AnotherNameRepoField(this, null);
		this.renamingUtils = new RenamingUtils(this, null);
	}

	public AnotherNameRepo(Renamer renamer,
			EmptyClass emptyClass) { /* Not used param */
		this(renamer);
	}

	public static RenamingUtils getRenamingUtils(AnotherNameRepo anotherNameRepo) {
		return anotherNameRepo.renamingUtils;
	}

	public static AnotherNameRepoField getAnotherRepoField(AnotherNameRepo anotherNameRepo) {
		return anotherNameRepo.anotherNameRepoField;
	}

	public static AnotherNameRepoLow2 getAnotherNameRepoLow(AnotherNameRepo anotherNameRepo) {
		return anotherNameRepo.anotherNameRepoLow;
	}
}
