namespace ArticleManager
{
    partial class UpdateReviewerForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.UpdateReviewer_button = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // UpdateReviewer_button
            // 
            this.UpdateReviewer_button.Location = new System.Drawing.Point(478, 245);
            this.UpdateReviewer_button.Name = "UpdateReviewer_button";
            this.UpdateReviewer_button.Size = new System.Drawing.Size(171, 34);
            this.UpdateReviewer_button.TabIndex = 3;
            this.UpdateReviewer_button.Text = "更新当前审稿人";
            this.UpdateReviewer_button.UseVisualStyleBackColor = true;
            this.UpdateReviewer_button.Click += new System.EventHandler(this.UpdateReviewer_button_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 12);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 27;
            this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridView1.Size = new System.Drawing.Size(460, 529);
            this.dataGridView1.TabIndex = 2;
            // 
            // UpdateReviewerForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(657, 550);
            this.Controls.Add(this.UpdateReviewer_button);
            this.Controls.Add(this.dataGridView1);
            this.Name = "UpdateReviewerForm";
            this.Text = "更新审稿人";
            this.Load += new System.EventHandler(this.UpdateReviewerForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button UpdateReviewer_button;
        private System.Windows.Forms.DataGridView dataGridView1;
    }
}