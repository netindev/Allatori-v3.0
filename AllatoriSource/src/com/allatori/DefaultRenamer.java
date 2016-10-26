package com.allatori;

public class DefaultRenamer {

	/* OK */

	private final Class101 anotherNameRepoLow;
	private final NameRepository nameRepository;
	private final RenamingUtils renamingUtils;
	private final ClassRenamer anotherNameRepoField;
	private final PackageRenamer nameRepositoryLow;

	public static NameRepository getNameRepository(DefaultRenamer anotherNameRepo) {
		return anotherNameRepo.nameRepository;
	}

	public static PackageRenamer getNameRepositoryLow(DefaultRenamer anotherNameRepo) {
		return anotherNameRepo.nameRepositoryLow;
	}

	private DefaultRenamer(Renamer renamer) { /* Not used param */
		this.anotherNameRepoLow = new Class101(this, null);
		this.nameRepositoryLow = new PackageRenamer(this, null);
		this.nameRepository = new NameRepository(this, null);
		this.anotherNameRepoField = new ClassRenamer(this, null);
		this.renamingUtils = new RenamingUtils(this, null);
	}

	public DefaultRenamer(Renamer renamer,
			EmptyClass emptyClass) { /* Not used param */
		this(renamer);
	}

	public static RenamingUtils getRenamingUtils(DefaultRenamer anotherNameRepo) {
		return anotherNameRepo.renamingUtils;
	}

	public static ClassRenamer getAnotherRepoField(DefaultRenamer anotherNameRepo) {
		return anotherNameRepo.anotherNameRepoField;
	}

	public static Class101 getAnotherNameRepoLow(DefaultRenamer anotherNameRepo) {
		return anotherNameRepo.anotherNameRepoLow;
	}
}
