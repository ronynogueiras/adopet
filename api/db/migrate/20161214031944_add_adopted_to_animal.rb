class AddAdoptedToAnimal < ActiveRecord::Migration[5.0]
  def change
    add_column :animals, :adopted, :boolean
  end
end
