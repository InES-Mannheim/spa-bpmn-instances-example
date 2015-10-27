package modules

import com.google.inject.AbstractModule
import org.openrdf.model.ValueFactory
import org.openrdf.repository.Repository
import org.openrdf.repository.sail.SailRepository
import org.openrdf.sail.memory.MemoryStore


class MemoryRepositoryModule extends AbstractModule{
  override def configure(): Unit = {
    val repo: Repository = memoryRepository
    val vf:ValueFactory = repo.getValueFactory

    bind(classOf[Repository]).toInstance(repo)
    bind(classOf[ValueFactory]).toInstance(vf)
  }

  def memoryRepository(): Repository = {
    val repo = new SailRepository(new MemoryStore())
    repo.initialize()
    repo
  }
}
