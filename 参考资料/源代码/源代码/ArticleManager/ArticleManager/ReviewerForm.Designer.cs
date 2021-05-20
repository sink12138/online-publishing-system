namespace ArticleManager
{
    partial class ReviewerForm
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
            this.AddReviewer_button = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // UpdateReviewer_button
            // 
            this.UpdateReviewer_button.Location = new System.Drawing.Point(64, 155);
            this.UpdateReviewer_button.Name = "UpdateReviewer_button";
            this.UpdateReviewer_button.Size = new System.Drawing.Size(155, 38);
            this.UpdateReviewer_button.TabIndex = 3;
            this.UpdateReviewer_button.Text = "更新审稿人";
            this.UpdateReviewer_button.UseVisualStyleBackColor = true;
            this.UpdateReviewer_button.Click += new System.EventHandler(this.UpdateAuthor_button_Click);
            // 
            // AddReviewer_button
            // 
            this.AddReviewer_button.Location = new System.Drawing.Point(64, 59);
            this.AddReviewer_button.Name = "AddReviewer_button";
            this.AddReviewer_button.Size = new System.Drawing.Size(155, 38);
            this.AddReviewer_button.TabIndex = 2;
            this.AddReviewer_button.Text = "添加审稿人";
            this.AddReviewer_button.UseVisualStyleBackColor = true;
            this.AddReviewer_button.Click += new System.EventHandler(this.AddAuthor_button_Click);
            // 
            // ReviewerForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(282, 253);
            this.Controls.Add(this.UpdateReviewer_button);
            this.Controls.Add(this.AddReviewer_button);
            this.Name = "ReviewerForm";
            this.Text = "添加/更新审稿人";
            this.Load += new System.EventHandler(this.ReviewerForm_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button UpdateReviewer_button;
        private System.Windows.Forms.Button AddReviewer_button;
    }
}