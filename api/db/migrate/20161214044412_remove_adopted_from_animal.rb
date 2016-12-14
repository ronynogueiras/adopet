class RemoveAdoptedFromAnimal < ActiveRecord::Migration[5.0]
  def change
    remove_column :animals, :adopted
  end
end
